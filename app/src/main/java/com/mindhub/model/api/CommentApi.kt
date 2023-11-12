package com.mindhub.model.api

import com.mindhub.model.entities.Comment
import com.mindhub.common.services.UserInfo

data class CreateCommentRequest(
    val content: String,
    val postId: Int
)

data class CreateReplyRequest(
    val content: String,
    val postId: Int,
    val commentId: Int,
)

interface CommentProvider {
    suspend fun create(data: CreateCommentRequest): Comment
    suspend fun get(postId: Int, page: Int): List<Comment>
    suspend fun vote(commentId: Int, score: Int)
    suspend fun createReply(data: CreateReplyRequest): Comment
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
                replies = mutableListOf(
                    Comment(
                        id = id++,
                        postId = 0,
                        username = "ecrt34",
                        content = "Bom demaise",
                        isBestAnswer = false,
                        score = 1,
                        userScore = 0,
                        replies = mutableListOf()
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
                replies = mutableListOf()
            )
        )
    }
    override suspend fun create(data: CreateCommentRequest): Comment {
        val comment = Comment(
            id = id++,
            postId = data.postId,
            username = UserInfo!!.username,
            content = data.content,
            isBestAnswer = false,
            score = 0,
            userScore = 0,
            replies = mutableListOf(),
        )

        comments.add(comment)

        return comment
    }

    override suspend fun get(postId: Int, page: Int): List<Comment> {
        return comments.filter { it.postId == postId }
    }

    override suspend fun vote(commentId: Int, score: Int) {
        val comment = comments.find { it.id == commentId } ?: throw Exception()
        comment.userScore = score
        comment.score += score
    }

    override suspend fun createReply(data: CreateReplyRequest): Comment {
        return Comment(
            id = id++,
            postId = data.postId,
            username = UserInfo!!.username,
            content = data.content,
            isBestAnswer = false,
            score = 0,
            userScore = 0,
            replies = mutableListOf(),
        )
    }
}

