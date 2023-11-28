package com.mindhub.model.api

import com.mindhub.common.services.Config
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

interface ProfileProvider {
    suspend fun getUserInformation(username: String): User
}

object ProfileApi: ProfileProvider {
    override suspend fun getUserInformation(username: String): User {
        val response: HttpResponse = Api.get("${Config.API_PREFIX}/user/$username")

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }
}

object ProfileFakeApi: ProfileProvider {
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
                )
            )
        )

        it.add(
            User(
                name = "Ana Maria",
                email = "ana@gmail.com",
                username = "anamaria",
                xp = 100,
                currentBadge = Badge("Aprendiz", 0),
                badges = listOf(),
                expertises = listOf(
                    Expertise("Geografia"),
                    Expertise("Física"),
                    Expertise("Inglês")
                )
            )
        )
    }

    override suspend fun getUserInformation(username: String): User {
        return users.find { it.username == username }!!
    }
}