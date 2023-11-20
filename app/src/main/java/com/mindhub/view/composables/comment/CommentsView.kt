package com.mindhub.view.composables.comment

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.model.entities.Comment
import com.mindhub.view.composables.Suspended
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.comment.GetCommentViewModel

@Composable
fun CommentsView(
    getCommentViewModel: GetCommentViewModel,
    showBestAnswerButton: Boolean,
    postId: Int,
    onScoreUpdate: (Int, Int) -> Unit,
    onRemove: (Int, Int?) -> Unit,
    onUpdate: (Int, Int?) -> Unit,
    onReply: (Int) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        getCommentViewModel.get(postId)
    }

    Suspended(
        isLoading = getCommentViewModel.isLoading
    ) {
        SpacedColumn(
            spacing = 8,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            for (comment in getCommentViewModel.comments) {
                CommentItem(
                    comment = comment,
                    showBestAnswerButton = showBestAnswerButton,
                    onScoreUpdate = onScoreUpdate,
                    onReply = onReply,
                    onRemove = onRemove,
                    onUpdate = onUpdate,
                    onMarkBestAnswer = { getCommentViewModel.toggleBestAnswer(it.id, postId) }
                )
            }
        }
    }
}