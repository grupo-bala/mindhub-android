package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Material
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

@Serializable
data class MaterialRequest(
    val title: String,
    val content: String,
    val expertise: String,
    val postDate: String,
)

interface MaterialProvider {
    suspend fun create(material: MaterialRequest): Material
    suspend fun update(materialUpdated: MaterialRequest, materialId: Int)
    suspend fun remove(materialId: Int)
    suspend fun getOne(id: Int): Material
    suspend fun getForYou(): List<Material>
    suspend fun getRecents(): List<Material>
    suspend fun getUserMaterials(username: String): List<Material>
}

object MaterialApi: MaterialProvider {
    override suspend fun create(material: MaterialRequest): Material {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/material") {
            contentType(ContentType.Application.Json)
            setBody(material)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun update(materialUpdated: MaterialRequest, materialId: Int) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/material/$materialId") {
            contentType(ContentType.Application.Json)
            setBody(materialUpdated)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun remove(materialId: Int) {
        val response: HttpResponse = Api.delete("${BuildConfig.apiPrefix}/material/$materialId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getOne(id: Int): Material {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/material/id/$id") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getForYou(): List<Material> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/material/for-you") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getRecents(): List<Material> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/material/recents") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getUserMaterials(username: String): List<Material> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/material/user/${username}") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }
}