package com.mindhub.model.api

import com.mindhub.model.entities.Expertise
import com.mindhub.services.Config
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class GetAllResponse(val expertises: List<Expertise>)
object ExpertiseApi {
    suspend fun getExpertise(title: String): Expertise {
        val response: HttpResponse = Api.get("${Config.API_PREFIX}/expertise/$title")

        if (response.status != HttpStatusCode.OK) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    suspend fun getAllExpertises(): List<Expertise> {
        val response: HttpResponse = Api.get("${Config.API_PREFIX}/expertise")

        if (response.status != HttpStatusCode.OK) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body<GetAllResponse>().expertises
    }
}