package com.mindhub.view.composables.post

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.common.services.UserInfo
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.RemoveConfirmationModal
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.comment.CommentsView
import com.mindhub.view.composables.comment.HandleComment
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.ask.GetAskViewModel
import com.mindhub.viewmodel.comment.HandleCommentViewModel
import com.mindhub.viewmodel.comment.GetCommentViewModel
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
    val handleCommentViewModel: HandleCommentViewModel = viewModel()
    val getCommentViewModel: GetCommentViewModel = viewModel()

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
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    if (viewModel.post == null) {
                        Text(text = viewModel.feedback)
                    } else {
                        PostInfo(
                            post = viewModel.post!!,
                            howManyComments = getCommentViewModel.comments.size,
                            navigator = navigator
                        ) {
                            viewModel.updateScore(it)
                        }
                        CommentsView(
                            getCommentViewModel = getCommentViewModel,
                            onScoreUpdate = { commentId, score ->
                                getCommentViewModel.updateScore(commentId, score)
                            },
                            postId = postId,
                            onRemove = { commentId, replyId ->
                                handleRemove = {
                                    handleCommentViewModel.removeComment(commentId, replyId) {
                                        getCommentViewModel.removeComment(commentId, replyId)
                                    }
                                }

                                isRemoveModalToggle = true
                            },
                            onReply = {
                                isCreateCommentMenuExpanded = true
                                commentIdToReply = it
                            },
                            onUpdate = { it1, it2 ->
                                isUpdateCommentMenuExpanded = true
                                commentToUpdate = Pair(it1, it2)
                            },
                            showBestAnswerButton = viewModel.instanceOf(GetAskViewModel::class)
                                    && !viewModel.isLoading
                                    && viewModel.post!!.user.username == UserInfo!!.username,
                        )
                    }
                }

                if (viewModel.post != null) {
                    FloatingActionButton(
                        onClick = { isCreateCommentMenuExpanded = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    }
                }

                if (isCreateCommentMenuExpanded) {
                    HandleComment(
                        isUpdate = false,
                        handleCommentViewModel = handleCommentViewModel,
                        onSuccess = {
                            isCreateCommentMenuExpanded = false

                            if (commentIdToReply != null) {
                                handleCommentViewModel.createReply(postId, commentIdToReply!!) { comment ->
                                     getCommentViewModel.addReply(comment, commentIdToReply!!)
                                }

                                commentIdToReply = null
                            } else {
                                handleCommentViewModel.createComment(postId) { comment ->
                                    getCommentViewModel.addComment(comment)
                                }
                            }

                            handleCommentViewModel.clear()
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
                        handleCommentViewModel = handleCommentViewModel,
                        onSuccess = {
                            isUpdateCommentMenuExpanded = false

                            handleCommentViewModel.updateComment(
                                commentToUpdate!!.first,
                                commentToUpdate!!.second,
                            ) {
                                getCommentViewModel.updateComment(
                                    commentToUpdate!!.first,
                                    commentToUpdate!!.second,
                                    it
                                )
                            }

                            commentToUpdate = null

                            handleCommentViewModel.clear()
                        },
                        onDismissRequest = {
                            isUpdateCommentMenuExpanded = false
                            commentToUpdate = null
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