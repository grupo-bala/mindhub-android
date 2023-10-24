package com.mindhub.view.composables.post

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindhub.model.entities.Post

@Composable
fun PostList(
    posts: List<Post>,
    onClick: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
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