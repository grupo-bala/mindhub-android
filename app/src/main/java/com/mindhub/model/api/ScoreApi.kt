package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class ScorePostRequest(
    val postId: Int,
    val value: Int
)

@Serializable
data class ScoreCommentRequest(
    val commentId: Int,
    val value: Int,
)

interface ScoreProvider {
    suspend fun vote(score: ScorePostRequest)
    suspend fun vote(score: ScoreCommentRequest)
}

object ScoreApi : ScoreProvider {
    override suspend fun vote(postScore: ScorePostRequest) {
        Api.post("${BuildConfig.apiPrefix}/score/post") {
            contentType(ContentType.Application.Json)
            setBody(postScore)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }
    }

    override suspend fun vote(scoreCommentRequest: ScoreCommentRequest) {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/score/comment") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${CurrentUser.token}")
            setBody(scoreCommentRequest)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)

            throw Exception(response.body<ApiError>().message)
        }
    }
}