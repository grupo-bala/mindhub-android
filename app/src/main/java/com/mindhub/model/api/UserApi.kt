package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.model.entities.Expertise
import com.mindhub.common.services.CurrentUser
import com.mindhub.common.services.UserInfo
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val expertises: List<String>
)

@Serializable
data class UpdateInfoRequest(
    val name: String,
    val email: String,
    val password: String,
    val expertises: List<Expertise>,
)

interface UserProvider {
    suspend fun login(params: LoginRequest)
    suspend fun register(params: RegisterRequest)
    suspend fun update(params: UpdateInfoRequest, profilePicture: ByteArray?)
}

object UserApi : UserProvider {
    override suspend fun login(params: LoginRequest) {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        val userInfo = response.body<UserInfo>()
        CurrentUser.user = userInfo.user
        CurrentUser.token = userInfo.token
    }

    override suspend fun register(params: RegisterRequest) {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/user") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        val userInfo = response.body<UserInfo>()
        CurrentUser.user = userInfo.user
        CurrentUser.token = userInfo.token
    }

    override suspend fun update(params: UpdateInfoRequest, profilePicture: ByteArray?) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/user/${CurrentUser.user!!.username}") {
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer ${CurrentUser.token}")
            }
            setBody(params)
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        if (profilePicture != null) {
            FileApi.uploadUser(CurrentUser.user!!.username, profilePicture)
        }
    }
}