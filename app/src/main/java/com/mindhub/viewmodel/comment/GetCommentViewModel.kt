package com.mindhub.viewmodel.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.CommentFakeApi
import com.mindhub.model.entities.Comment
import com.mindhub.common.services.ErrorParser
import kotlinx.coroutines.launch

class GetCommentViewModel : ViewModel() {
    var comments = mutableStateListOf<Comment>()
    var feedback by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun get(postId: Int) {
        viewModelScope.launch {
            try {
                feedback = ""
                isLoading = true
                comments.addAll(CommentFakeApi.get(postId, 1))
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)
            }

            isLoading = false
        }
    }

    fun addComment(comment: Comment) {
        comments.add(comment)

        comments.sortByDescending { it.score }
    }

    fun addReply(comment: Comment, commentId: Int) {
        val toReply = comments.find {
            it.id == commentId
        } ?: throw Exception()

        toReply.replies.add(comment)
    }

    fun updateScore(commentId: Int, score: Int) {
        val index = comments.indexOfFirst { it.id == commentId }

        val updatedComment = comments[index]

        comments.remove(updatedComment)

        updatedComment.score = score

        comments.add(updatedComment)

        comments.sortByDescending { it.score }
    }
}