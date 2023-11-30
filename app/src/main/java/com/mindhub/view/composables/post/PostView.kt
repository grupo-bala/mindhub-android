package com.mindhub.view.composables.post

import androidx.compose.foundation.Image
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.R
import com.mindhub.common.services.CurrentUser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.ErrorModal
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

    var isErrorModalToggle by remember {
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
                                commentViewModel.updateScore(commentId, score) {
                                    isErrorModalToggle = true
                                }
                            },
                            postId = postId,
                            onRemove = { commentId, replyTo ->
                                handleRemove = {
                                    commentViewModel.removeComment(commentId, replyTo) {
                                        isErrorModalToggle = true
                                    }
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
                            isErrorModalToggle = true
                        }
                    }
                }

                if (viewModel.post != null) {
                    FloatingActionButton(
                        onClick = { isCreateCommentMenuExpanded = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
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
                                    isErrorModalToggle = true
                                }

                                commentIdToReply = null
                            } else {
                                commentViewModel.addComment(postId, handleCommentCreationViewModel.commentText) {
                                    isErrorModalToggle = true
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
                                isErrorModalToggle = true
                            }

                            commentToUpdate = null

                            handleCommentCreationViewModel.clear()
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

                if (isErrorModalToggle) {
                    ErrorModal(
                        text = commentViewModel.feedback,
                        onConfirmation = {
                            isErrorModalToggle = false
                            commentViewModel.feedback = ""
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