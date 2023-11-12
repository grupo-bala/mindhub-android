package com.mindhub.viewmodel.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.model.api.CommentFakeApi
import com.mindhub.model.api.CreateCommentRequest
import com.mindhub.model.api.CreateReplyRequest
import com.mindhub.model.entities.Comment
import kotlinx.coroutines.launch

class CreateCommentViewModel : ViewModel() {
    var commentText by mutableStateOf("")

    fun createComment(postId: Int, onSuccess: (Comment) -> Unit) {
        viewModelScope.launch {
            try {
                val comment = CommentFakeApi.create(CreateCommentRequest(commentText, postId))

                onSuccess(comment)
            } catch (e: Exception) {
                /* TODO */
            }
        }
    }

    fun createReply(postId: Int, commentId: Int, onSuccess: (Comment) -> Unit) {
        viewModelScope.launch {
            try {
                val comment = CommentFakeApi.createReply(CreateReplyRequest(commentText, postId, commentId))

                onSuccess(comment)
            } catch (e: Exception) {
                /* TODO */
            }
        }
    }

    fun clear() {
        commentText = ""
    }
}