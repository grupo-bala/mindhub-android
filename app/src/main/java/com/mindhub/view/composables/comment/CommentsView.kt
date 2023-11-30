package com.mindhub.view.composables.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import com.mindhub.view.composables.Suspended
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.comment.CommentViewModel

@Composable
fun CommentsView(
    getCommentViewModel: CommentViewModel,
    showBestAnswerButton: Boolean,
    postId: Int,
    onScoreUpdate: (Int, Int) -> Unit,
    onRemove: (Int, Int?) -> Unit,
    onUpdate: (Int, Int?, String) -> Unit,
    onReply: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onFailure: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        getCommentViewModel.get(postId, onFailure = onFailure)
    }

    Suspended(
        isLoading = getCommentViewModel.isLoading
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            items(getCommentViewModel.comments) {
                CommentItem(
                    comment = it,
                    showBestAnswerButton = showBestAnswerButton,
                    onScoreUpdate = onScoreUpdate,
                    onReply = onReply,
                    onRemove = onRemove,
                    onUpdate = onUpdate,
                    onMarkBestAnswer = { targetComment ->
                        getCommentViewModel.toggleBestAnswer(targetComment.id, postId, onFailure)
                    },
                    onNavigate = onNavigate,
                )
            }
        }
    }
}