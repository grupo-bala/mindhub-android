package com.mindhub.view.composables.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindhub.view.composables.Suspended
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
            verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically),
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
                
                Spacer(modifier = Modifier
                    .padding(top = 20.dp, start = 4.dp, end = 4.dp, bottom = 4.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}