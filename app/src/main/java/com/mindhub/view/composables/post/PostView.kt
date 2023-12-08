package com.mindhub.view.composables.post

import androidx.compose.foundation.Image
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.R
import com.mindhub.common.services.CurrentUser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.RemoveConfirmationModal
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.comment.CommentsView
import com.mindhub.view.composables.comment.HandleComment
import com.mindhub.view.destinations.ProfileDestination
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.ask.GetAskViewModel
import com.mindhub.viewmodel.comment.HandleCommentCreationViewModel
import com.mindhub.viewmodel.comment.CommentViewModel
import com.mindhub.viewmodel.post.GetPostViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import io.ktor.util.reflect.instanceOf

@Composable
fun PostView(
    postId: Int,
    viewModel: GetPostViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    val handleCommentCreationViewModel: HandleCommentCreationViewModel = viewModel()
    val commentViewModel: CommentViewModel = viewModel()

    var isCreateCommentMenuExpanded by remember {
        mutableStateOf(false)
    }

    var isUpdateCommentMenuExpanded by remember {
        mutableStateOf(false)
    }

    var isRemoveModalToggle by remember {
        mutableStateOf(false)
    }

    var commentIdToReply by remember {
        mutableStateOf<Int?>(null)
    }

    var commentToUpdate by remember {
        mutableStateOf<Pair<Int, Int?>?>(null)
    }

    var handleRemove: () -> Unit = {}

    LaunchedEffect(key1 = true) {
        viewModel.get(postId)
    }

    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator,
        hasBackArrow = true,
        bottomAppBar = {}
    ) {
        Suspended(
            isLoading = viewModel.isLoading
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SpacedColumn(
                    spacing = 8,
                    verticalAlignment = Alignment.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (viewModel.post == null) {
                        Text(text = viewModel.feedback)
                    } else {
                        PostInfo(
                            post = viewModel.post!!,
                            howManyComments = commentViewModel.comments.size,
                            navigator = navigator
                        ) {
                            viewModel.updateScore(it)
                        }

                        CommentsView(
                            getCommentViewModel = commentViewModel,
                            onScoreUpdate = { commentId, score ->
                                commentViewModel.updateScore(
                                    commentId,
                                    score,
                                ) {
                                    Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
                                }
                            },
                            postId = postId,
                            onRemove = { commentId, replyTo ->
                                handleRemove = {
                                    commentViewModel.removeComment(
                                        commentId,
                                        replyTo,
                                        onFailure = {
                                            Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
                                        },
                                    )
                                }

                                isRemoveModalToggle = true
                            },
                            onReply = {
                                isCreateCommentMenuExpanded = true
                                commentIdToReply = it
                            },
                            onUpdate = { it1, it2, it3 ->
                                isUpdateCommentMenuExpanded = true
                                commentToUpdate = Pair(it1, it2)
                                handleCommentCreationViewModel.commentText = it3
                            },
                            showBestAnswerButton = viewModel.instanceOf(GetAskViewModel::class)
                                    && !viewModel.isLoading
                                    && viewModel.post!!.user.username == CurrentUser.user!!.username,
                            onNavigate = {
                                navigator.navigate(ProfileDestination(it))
                            },
                        ) {
                            Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                if (viewModel.post != null) {
                    FloatingActionButton(
                        onClick = { isCreateCommentMenuExpanded = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                            .semantics {
                                contentDescription = "AddCommentButton"
                            }
                    ) {
                        Image(imageVector = ImageVector
                            .vectorResource(R.drawable.baseline_add_comment_24),
                            contentDescription = null
                        )
                    }
                }

                if (isCreateCommentMenuExpanded) {
                    HandleComment(
                        isUpdate = false,
                        handleCommentViewModel = handleCommentCreationViewModel,
                        onSuccess = {
                            isCreateCommentMenuExpanded = false

                            if (commentIdToReply != null) {
                                commentViewModel.addReply(postId, commentIdToReply!!, handleCommentCreationViewModel.commentText) {
                                    Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
                                }

                                commentIdToReply = null
                            } else {
                                commentViewModel.addComment(
                                    postId,
                                    handleCommentCreationViewModel.commentText,
                                ) {
                                    Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
                                }
                            }

                            handleCommentCreationViewModel.clear()
                        },
                        onDismissRequest = {
                            isCreateCommentMenuExpanded = false
                            commentIdToReply = null
                        },
                    )
                }

                if (isUpdateCommentMenuExpanded) {
                    HandleComment(
                        isUpdate = true,
                        handleCommentViewModel = handleCommentCreationViewModel,
                        onSuccess = {
                            isUpdateCommentMenuExpanded = false

                            commentViewModel.updateComment(
                                commentToUpdate!!.first,
                                commentToUpdate!!.second,
                                handleCommentCreationViewModel.commentText
                            ) {
                                Toast.makeText(context, "Algo deu errado", Toast.LENGTH_SHORT).show()
                            }

                            commentToUpdate = null

                            handleCommentCreationViewModel.clear()

                            Toast.makeText(context, "Comentário alterado", Toast.LENGTH_SHORT).show()
                        },
                        onDismissRequest = {
                            isUpdateCommentMenuExpanded = false
                            commentToUpdate = null
                            handleCommentCreationViewModel.clear()
                        }
                    )
                }

                if (isRemoveModalToggle) {
                    RemoveConfirmationModal(
                        onConfirmation = {
                            handleRemove()
                            isRemoveModalToggle = false
                            handleRemove = {}
                        },
                        onDismissRequest = {
                            handleRemove = {}
                            isRemoveModalToggle = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostViewPreview() {
    val viewModel: GetAskViewModel = viewModel()

    MindHubTheme {
        PostView(
            navigator = EmptyDestinationsNavigator,
            postId = 0,
            viewModel = viewModel
        )
    }
}