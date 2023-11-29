package com.mindhub.viewmodel.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.ScoreApi
import com.mindhub.model.api.ScoreRequest
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
                ScoreApi.vote(ScoreRequest(post!!.id, 0))
            } else {
                post!!.score += post!!.userScore * -1
                post!!.userScore = userScore
                post!!.score += userScore

                ScoreApi.vote(ScoreRequest(post!!.id, userScore))
            }
        }

        post = post
    }
}