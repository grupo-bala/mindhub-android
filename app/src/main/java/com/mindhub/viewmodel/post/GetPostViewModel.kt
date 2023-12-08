package com.mindhub.viewmodel.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.ScoreApi
import com.mindhub.model.api.ScorePostRequest
import com.mindhub.model.entities.Post
import kotlinx.coroutines.launch

abstract class GetPostViewModel : ViewModel() {
    abstract var post: Post?
    abstract var feedback: String
    abstract var isLoading: Boolean

    abstract fun get(id: Int)

    fun updateScore(userScore: Int) {
        viewModelScope.launch {
            if (userScore == post!!.userScore) {
                post!!.score += post!!.userScore * -1
                post!!.userScore = 0
            } else {
                post!!.score += post!!.userScore * -1
                post!!.score += userScore
                post!!.userScore = userScore
            }

            ScoreApi.vote(ScorePostRequest(post!!.id, post!!.userScore))
        }

        post = post
    }
}