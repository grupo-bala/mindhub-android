package com.mindhub.view.composables.post

import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.RemoveConfirmationModal
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.comment.CommentsView
import com.mindhub.view.composables.comment.CreateComment
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.ask.GetAskViewModel
import com.mindhub.viewmodel.comment.CreateCommentViewModel
import com.mindhub.viewmodel.comment.GetCommentViewModel
import com.mindhub.viewmodel.post.GetPostViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Composable
fun PostView(
    postId: Int,
    viewModel: GetPostViewModel,
    navigator: DestinationsNavigator
) {
    val createCommentViewModel: CreateCommentViewModel = viewModel()
    val getCommentViewModel: GetCommentViewModel = viewModel()

    var isCreateCommentMenuExpanded by remember {
        mutableStateOf(false)
    }

    var isRemoveModalToggle by remember {
        mutableStateOf(false)
    }

    var commentIdToReply by remember {
        mutableStateOf<Int?>(null)
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
                                    getCommentViewModel.removeComment(commentId, replyId)
                                }

                                isRemoveModalToggle = true
                            },
                            onReply = {
                                isCreateCommentMenuExpanded = true
                                commentIdToReply = it
                            }
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
                    CreateComment(
                        isReply = commentIdToReply != null,
                        createCommentViewModel = createCommentViewModel,
                        onCreate = {
                            isCreateCommentMenuExpanded = false

                            if (commentIdToReply != null) {
                                createCommentViewModel.createReply(postId, commentIdToReply!!) { comment ->
                                     getCommentViewModel.addReply(comment, commentIdToReply!!)
                                }

                                commentIdToReply = null
                            } else {
                                createCommentViewModel.createComment(postId) { comment ->
                                    getCommentViewModel.addComment(comment)
                                }
                            }

                            createCommentViewModel.clear()
                        },
                        onDismissRequest = {
                            isCreateCommentMenuExpanded = false
                            commentIdToReply = null
                        },
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