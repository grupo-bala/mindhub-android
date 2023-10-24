package com.mindhub.model.api

import com.mindhub.model.entities.Comment
import com.mindhub.common.services.UserInfo

data class CreateCommentRequest(
    val content: String,
    val postId: Int
)
interface CommentProvider {
    suspend fun create(data: CreateCommentRequest)
    suspend fun get(postId: Int, page: Int): List<Comment>
    suspend fun vote(commentId: Int, score: Int)
}

object CommentFakeApi : CommentProvider {
    private var id = 0
    private val comments = mutableListOf<Comment>().also {
        it.add(
            Comment(
                id = id++,
                postId = 0,
                username = "jjaum",
                content = "Massa demais tu é doido",
                isBestAnswer = true,
                score = 4,
                userScore = 0,
                replies = listOf(
                    Comment(
                        id = id++,
                        postId = 0,
                        username = "ecrt34",
                        content = "Bom demaise",
                        isBestAnswer = false,
                        score = 1,
                        userScore = 0,
                        replies = listOf()
                    )
                )
            )
        )

        it.add(
            Comment(
                id = id++,
                postId = 0,
                username = "teste76",
                content = "Lorem ipsum",
                isBestAnswer = false,
                score = -2,
                userScore = 0,
                replies = listOf()
            )
        )
    }
    override suspend fun create(data: CreateCommentRequest) {
        val comment = Comment(
            id = id,
            postId = data.postId,
            username = UserInfo!!.username,
            content = data.content,
            isBestAnswer = false,
            score = 0,
            userScore = 0,
            replies = listOf(),
        )

        comments.add(comment)

        id += 1
    }

    override suspend fun get(postId: Int, page: Int): List<Comment> {
        return comments.filter { it.postId == postId }
    }

    override suspend fun vote(commentId: Int, score: Int) {
        val comment = comments.find { it.id == commentId } ?: throw Exception()
        comment.userScore = score
        comment.score += score
    }
}

