package com.mindhub.view.composables.post

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Post
import com.mindhub.view.composables.Suspended

@Composable
fun PostList(
    posts: List<Post>,
    onClick: (Post) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Suspended(isLoading = isLoading, loadingSize = 64.dp) {
        LazyColumn(modifier = modifier) {
            items(posts) {
                PostItem(
                    post = it,
                    onClick = { onClick(it) }
                )
            }
        }
    }
}