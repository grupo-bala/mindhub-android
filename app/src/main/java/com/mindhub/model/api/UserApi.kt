package com.mindhub.model.api

import com.mindhub.services.Config
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val expertises: List<String>
)

@Serializable
data class RegisterResponse(val token: String)

object UserApi {
    suspend fun login(params: LoginRequest): LoginResponse {
        val response: HttpResponse = Api.post("${Config.API_PREFIX}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    suspend fun register(params: RegisterRequest): RegisterResponse {
        val response: HttpResponse = Api.post("${Config.API_PREFIX}/user") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }
}