package com.mindhub.view.composables.post

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.mindhub.model.entities.Post

@Composable
fun PostList(
    posts: List<Post>,
    onClick: (Post) -> Unit
) {
    LazyColumn() {
        items(posts) {
            PostItem(
                title = it.title,
                description = it.content,
                score = it.score,
                onClick = { onClick(it) }
            )
        }
    }
}