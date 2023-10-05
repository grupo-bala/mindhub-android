package com.mindhub.model.api

import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.services.Config
import com.mindhub.services.UserInfo
import io.ktor.client.call.body
import io.ktor.client.request.headers
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
data class LoginRequest(
    val username: String,
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
data class UpdateRequest(
    val name: String,
    val email: String,
    val expertises: List<Expertise>,
// TODO:    val badge: String
)

@Serializable
data class RegisterResponse(val token: String)

object UserApi {
    suspend fun login(params: LoginRequest) {
        val response: HttpResponse = Api.post("${Config.API_PREFIX}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            throw Exception(response.body<ApiError>().message)
        }

        UserInfo = response.body<User>()
    }

    suspend fun register(params: RegisterRequest) {
        val response: HttpResponse = Api.post("${Config.API_PREFIX}/user") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            throw Exception(response.body<ApiError>().message)
        }

        println(response.bodyAsText())
        UserInfo = response.body<User>()
    }

    suspend fun update(params: UpdateRequest) {
        println("ENVIANDO")
        println(params)
        println("name: ${UserInfo!!.username}")

        val response: HttpResponse = Api.patch("${Config.API_PREFIX}/user/${UserInfo!!.username}") {
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer ${UserInfo!!.token}")
            }
            setBody(params)
        }

        println("STATUS: ${response.status}")
    }
}