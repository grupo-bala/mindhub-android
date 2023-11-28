package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.model.entities.Expertise
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

interface ExpertiseProvider {
    suspend fun getExpertise(title: String): Expertise
    suspend fun getAllExpertises(): List<Expertise>
}

object ExpertiseApi : ExpertiseProvider {
    override suspend fun getExpertise(title: String): Expertise {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/expertise/$title")

        if (response.status != HttpStatusCode.OK) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getAllExpertises(): List<Expertise> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/expertise")

        if (response.status != HttpStatusCode.OK) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }
}

object ExpertiseFakeApi : ExpertiseProvider {
    private val expertises = listOf(
        Expertise("Matemática"),
        Expertise("Português"),
        Expertise("Ciências"),
        Expertise("História"),
        Expertise("Geografia"),
        Expertise("Inglês"),
        Expertise("Física"),
        Expertise("Química"),
        Expertise("Educação Física"),
        Expertise("Biologia"),
        Expertise("Filosofia"),
        Expertise("Sociologia"),
        Expertise("Artes"),
        Expertise("Música"),
        Expertise("Línguas Estrangeiras"),
        Expertise("Programação"),
        Expertise("Robótica")
    )

    override suspend fun getExpertise(title: String): Expertise {
        return expertises.find { it.title == title } ?: throw Exception()
    }

    override suspend fun getAllExpertises(): List<Expertise> {
        return expertises
    }
}