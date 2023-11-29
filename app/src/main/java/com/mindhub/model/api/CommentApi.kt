package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.model.entities.Comment
import com.mindhub.common.services.CurrentUser
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentRequest(
    val content: String,
    val postId: Int
)

@Serializable
data class CreateReplyRequest(
    val content: String,
    val postId: Int,
    val replyTo: Int,
)

@Serializable
data class UpdateCommentRequest(
    val content: String
)

@Serializable
data class UpdateBestAnswerRequest(
    val postId: Int,
)

interface CommentProvider {
    suspend fun create(comment: CreateCommentRequest): Comment
    suspend fun createReply(reply: CreateReplyRequest): Comment
    suspend fun findAll(postId: Int): List<Comment>
    suspend fun remove(commentId: Int)
    suspend fun update(commentId: Int, newContent: UpdateCommentRequest)

    // TODO: Implement toggleBestAnswer
    suspend fun toggleBestAnswer(commentId: Int, postId: Int)
}

object CommentApi : CommentProvider {
    override suspend fun create(comment: CreateCommentRequest): Comment {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/comment/") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${CurrentUser.token}")
            setBody(comment)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }

        println(response.bodyAsText())

        return response.body()
    }

    override suspend fun createReply(reply: CreateReplyRequest): Comment {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/comment/reply") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${CurrentUser.token}")
            setBody(reply)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun findAll(postId: Int): List<Comment> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/comment/$postId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun remove(commentId: Int) {
        val response: HttpResponse = Api.delete("${BuildConfig.apiPrefix}/comment/$commentId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }
    }

    override suspend fun update(commentId: Int, newContent: UpdateCommentRequest) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/comment/${commentId}") {
            contentType(ContentType.Application.Json)
            setBody(newContent)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }
    }

    override suspend fun toggleBestAnswer(commentId: Int, postId: Int) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/comment/best-answer/$commentId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
            setBody(UpdateBestAnswerRequest(postId))
            contentType(ContentType.Application.Json)
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }
    }
}

