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
            println("AAWDIJDWIDJWIDJWJDI\n\n")
            println(userScore)
            post!!.score += post!!.userScore * -1
            post!!.userScore = userScore
            post!!.score += userScore
        }

        post = post
    }
}