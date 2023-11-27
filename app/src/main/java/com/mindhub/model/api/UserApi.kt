package com.mindhub.model.api

import android.net.Uri
import com.mindhub.common.serialize.UriSerializer
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.common.services.Config
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
data class UpdateRequest(
    val name: String,
    val email: String,
    val expertises: List<Expertise>,
    val badge: Badge,
    @Serializable(UriSerializer::class)
    val profilePicture: Uri?,
)

interface UserProvider {
    suspend fun login(params: LoginRequest)
    suspend fun register(params: RegisterRequest)
    suspend fun update(params: UpdateRequest)
}

object UserApi : UserProvider {
    override suspend fun login(params: LoginRequest) {
        val response: HttpResponse = Api.post("${Config.API_PREFIX}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        UserInfo = response.body<User>()

        println(UserInfo!!)
    }

    override suspend fun register(params: RegisterRequest) {
        val response: HttpResponse = Api.post("${Config.API_PREFIX}/user") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        UserInfo = response.body<User>()
    }

    override suspend fun update(params: UpdateRequest) {
        val response: HttpResponse = Api.patch("${Config.API_PREFIX}/user/${UserInfo!!.username}") {
            contentType(ContentType.Application.Json)
            headers {
                append("Authorization", "Bearer ${UserInfo!!.token}")
            }
            setBody(params)
        }
    }
}

object UserFakeApi : UserProvider {
    private val users = mutableListOf<User>().also {
        it.add(
            User(
                name = "Administrador",
                email = "admin@admin.com",
                username = "admin",
                xp = 0,
                currentBadge = Badge("Aprendiz", 0),
                badges = listOf(),
                expertises = listOf(
                    Expertise("Matemática"),
                    Expertise("Física"),
                    Expertise("Inglês")
                ),
                token = ""
            )
        )
    }

    override suspend fun login(params: LoginRequest) {
        UserInfo = users.find { it.email == params.email && params.password == "123" }
            ?: throw Exception("WRONG CREDENTIALS")
    }

    override suspend fun register(params: RegisterRequest) {
        if (users.find { it.username == params.username } != null) {
            throw Exception("DUPLICATE USERNAME")
        } else if (users.find { it.email == params.email } != null) {
            throw Exception("DUPLICATE EMAIL")
        }

        val user = User(
            name = params.name,
            email = params.email,
            username = params.username,
            xp = 0,
            currentBadge = Badge("Aprendiz", 0),
            badges = listOf(),
            expertises = params.expertises.map { Expertise(it) },
            token = ""
        )

        users.add(user)
        UserInfo = user
    }

    override suspend fun update(params: UpdateRequest) {
        val user = users.find { it.username == UserInfo!!.username }
            ?: throw Exception()

        user.name = params.name
        user.email = params.email
        user.expertises = params.expertises
        user.currentBadge = params.badge
        user.profilePicture = params.profilePicture

        UserInfo = user
    }
}