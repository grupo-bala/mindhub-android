package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class ScoreRequest(
    val postId: Int,
    val value: Int
)

object ScoreApi {
    suspend fun vote(score: ScoreRequest) {
        Api.post("${BuildConfig.apiPrefix}/score") {
            contentType(ContentType.Application.Json)
            setBody(score)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }
    }
}