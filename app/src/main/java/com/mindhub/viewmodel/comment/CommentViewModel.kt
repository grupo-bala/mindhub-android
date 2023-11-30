package com.mindhub.viewmodel.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.entities.Comment
import com.mindhub.common.services.ErrorParser
import com.mindhub.model.api.CommentApi
import com.mindhub.model.api.CreateCommentRequest
import com.mindhub.model.api.CreateReplyRequest
import com.mindhub.model.api.ScoreApi
import com.mindhub.model.api.ScoreCommentRequest
import com.mindhub.model.api.UpdateCommentRequest
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {
    var comments = mutableStateListOf<Comment>()
    var feedback by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var update by mutableStateOf(false)

    fun get(postId: Int, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                feedback = ""
                isLoading = true

                comments.clear()

                val responseComments = CommentApi.findAll(postId)

                comments.addAll(
                    responseComments.sortedWith(
                        compareByDescending<Comment> { it.isBestAnswer }.thenByDescending { it.score }
                    )
                )
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }

            isLoading = false
        }
    }

    fun addComment(postId: Int, content: String, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                feedback = ""

                val addedComment =
                    CommentApi.create(CreateCommentRequest(content, postId))

                comments.add(addedComment)

                comments.sortWith(
                    compareByDescending<Comment> { it.isBestAnswer }.thenByDescending { it.score }
                )
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }
        }
    }

    fun addReply(postId: Int, commentId: Int, content: String, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                feedback = ""

                val addedReply = CommentApi.createReply(CreateReplyRequest(content, postId, commentId))

                val targetComment = comments.find { it.id == commentId } ?: throw Exception()
                val index = comments.indexOf(targetComment)

                comments.remove(targetComment)

                targetComment.replies.add(addedReply)

                comments.add(index, targetComment)
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }
        }
    }

    fun updateScore(commentId: Int, userScore: Int, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                feedback = ""

                val targetComment = comments.find { it.id == commentId } ?: throw Exception()
                val index = comments.indexOf(targetComment)

                if (userScore == targetComment.userScore) {
                    targetComment.score += targetComment.userScore * -1
                    targetComment.userScore = 0
                } else {
                    targetComment.score += targetComment.userScore * -1
                    targetComment.score += userScore
                    targetComment.userScore = userScore
                }

                ScoreApi.vote(ScoreCommentRequest(commentId, targetComment.userScore))

                comments.removeAt(index)
                comments.add(index, targetComment)

                comments.sortWith(
                    compareByDescending<Comment> { it.isBestAnswer }.thenByDescending { it.score }
                )
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }
        }
    }

    fun removeComment(commentId: Int, replyTo: Int?, onFailure: () -> Unit, onSucces: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                feedback = ""

                CommentApi.remove(commentId)

                if (replyTo != null) {
                    val targetComment = comments.find { it.id == replyTo } ?: throw Exception()
                    val index = comments.indexOf(targetComment)

                    comments.remove(targetComment)

                    targetComment.replies.removeIf { it.id == commentId }

                    comments.add(index, targetComment)
                } else {
                    comments.removeIf { it.id == commentId }
                }

                onSucces()
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }
        }
    }

    fun updateComment(commentId: Int, replyTo: Int?, newContent: String, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                feedback = ""

                CommentApi.update(commentId, UpdateCommentRequest(newContent))

                val targetComment = comments.find { it.id == (replyTo ?: commentId) } ?: throw Exception()
                val index = comments.indexOf(targetComment)

                comments.remove(targetComment)

                if (replyTo != null) {
                    val targetReply = targetComment.replies.find { it.id == commentId } ?: throw Exception()

                    targetReply.content = newContent
                } else {
                    targetComment.content = newContent
                }

                comments.add(index, targetComment)
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }
        }
    }
    
    fun toggleBestAnswer(commentId: Int, postId: Int, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                feedback = ""

                CommentApi.toggleBestAnswer(commentId, postId)

                val oldBestAnswer = comments.find { it.isBestAnswer }

                if (oldBestAnswer != null) {
                    oldBestAnswer.isBestAnswer = false
                }

                if (oldBestAnswer != null && oldBestAnswer.id == commentId) {
                    val targetComment = comments.find { it.id == commentId } ?: throw Exception()
                    val index = comments.indexOf(targetComment)

                    comments.remove(targetComment)
                    comments.add(index, targetComment)

                    return@launch
                }

                val targetComment = comments.find { it.id == commentId } ?: throw Exception()
                val index = comments.indexOf(targetComment)

                comments.remove(targetComment)

                targetComment.isBestAnswer = true

                comments.add(index, targetComment)

                comments.sortWith(
                    compareByDescending<Comment> { it.isBestAnswer }.thenByDescending { it.score }
                )
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)

                onFailure()
            }
        }
    }
}