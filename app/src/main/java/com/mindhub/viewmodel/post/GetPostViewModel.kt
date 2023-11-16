package com.mindhub.viewmodel.post

import com.mindhub.model.entities.Post

interface GetPostViewModel {
    var post: Post?
    var feedback: String
    var isLoading: Boolean

    fun get(id: Int)

    fun updateScore(userScore: Int) {
        if (userScore == post!!.userScore) {
            post!!.score += post!!.userScore * -1
            post!!.userScore = 0
        } else {
            post!!.score += userScore
            post!!.userScore = userScore
        }

        post = post
    }
}