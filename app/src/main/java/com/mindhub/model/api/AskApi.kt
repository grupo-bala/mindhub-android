package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Ask
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

interface AskProvider {
    suspend fun create(ask: AskRequest, img: ByteArray?): Ask
    suspend fun update(askUpdated: AskRequest, askId: Int, img: ByteArray?)
    suspend fun remove(askId: Int)
    suspend fun getOne(askId: Int): Ask
    suspend fun get(askTitle: String): List<Ask>
    suspend fun getForYou(): List<Ask>
    suspend fun getRecents(): List<Ask>
    suspend fun getUserAsks(username: String): List<Ask>
}

@Serializable
data class AskRequest(
    val title: String,
    val content: String,
    val expertise: String,
    val postDate: String,
    val hasImage: Boolean
)

object AskApi: AskProvider {
    override suspend fun create(ask: AskRequest, img: ByteArray?): Ask {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/ask") {
            contentType(ContentType.Application.Json)
            setBody(ask)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        val createdAsk = response.body<Ask>()

        if (img != null) {
            FileApi.uploadPost("ask", createdAsk.id, img)
        }

        return createdAsk
    }

    override suspend fun update(askUpdated: AskRequest, askId: Int, img: ByteArray?) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/ask/$askId") {
            contentType(ContentType.Application.Json)
            setBody(askUpdated)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        if (img != null) {
            FileApi.uploadPost("ask", askId, img)

        }

        return response.body()
    }

    override suspend fun remove(askId: Int) {
        val response: HttpResponse = Api.delete("${BuildConfig.apiPrefix}/ask/$askId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getOne(askId: Int): Ask {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/id/$askId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun get(askTitle: String): List<Ask> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/search/$askTitle") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getForYou(): List<Ask> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/for-you") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getRecents(): List<Ask> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/recents") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getUserAsks(username: String): List<Ask> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/user/${username}") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

}