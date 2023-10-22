package com.mindhub.view.composables.comment

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.Suspended
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.comment.GetCommentViewModel

@Composable
fun CommentsView(
    postId: Int
) {
    val viewModel: GetCommentViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        viewModel.get(postId)
    }

    Suspended(
        isLoading = viewModel.isLoading
    ) {
        SpacedColumn(
            spacing = 8,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            for (comment in viewModel.comments) {
                CommentItem(comment = comment)
            }
        }
    }
}