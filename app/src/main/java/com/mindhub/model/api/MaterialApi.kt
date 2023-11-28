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
    suspend fun update(materialUpdated: Material)
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

    override suspend fun update(materialUpdated: Material) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/material/${materialUpdated.id}") {
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
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/material/id/$id")

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getForYou(): List<Material> {
        return listOf()
    }

    override suspend fun getRecents(): List<Material> {
        return listOf()
    }

    override suspend fun getUserMaterials(username: String): List<Material> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/material/user/${username}")

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }
}

//object MaterialFakeApi: MaterialProvider {
//    private val materials = mutableListOf<Material>().also {
//        val user = User(
//            "João",
//            "joaum123@gmail.com",
//            "jjaum",
//            0,
//            Badge("", 0),
//            listOf(),
//            listOf(),
//            "",
//            null
//        )
//        it.add(Material(IdManager.id++, user, "Relações trigonométricas em 1 minuto!", userScore = 0, "teste", 87, LocalDateTime.now(), Expertise("Matemática")))
//        it.add(Material(IdManager.id++, user, "As três leis de Newton com exemplos", userScore = 0, "teste", 87, LocalDateTime.now(), Expertise("Física")))
//        it.add(Material(IdManager.id++, user, "Os biomas brasileiros explicados", userScore = 0, "teste", 87, LocalDateTime.now(), Expertise("Geografia")))
//    }
//
//    override suspend fun create(material: MaterialRequest): Material {
//        val newMaterial = Material(
//            id = IdManager.id++,
//            title = material.title,
//            content = material.content,
//            expertise = Expertise(material.expertise),
//            user = UserInfo!!,
//            postDate = LocalDateTime.now(),
//            userScore = 0,
//            score = 0
//        )
//
//        materials.add(newMaterial)
//
//        return newMaterial
//    }
//
//    override suspend fun update(materialUpdated: Material) {
//        val material = materials.find { it.id == materialUpdated.id } ?: throw Exception()
//
//        material.content = materialUpdated.content
//        material.title = materialUpdated.title
//        material.expertise = materialUpdated.expertise
//    }
//
//    override suspend fun remove(materialId: Int) {
//        if (!materials.removeIf { it.id == materialId }) {
//            throw Exception()
//        }
//    }
//
//    override suspend fun getOne(id: Int): Material {
//        return materials.find { it.id == id } ?: throw Exception()
//    }
//
//    override suspend fun getUserMaterials(username: String): List<Material> {
//        val filtered = materials.filter {
//            it.user.username == username
//        }
//
//        return filtered.reversed()
//    }
//
//    override suspend fun getForYou(): List<Material> {
//        val filtered = materials.filter {
//            it.expertise in UserInfo!!.expertises && it.user.username != UserInfo!!.username
//        }
//
//        return filtered.sortedBy { it.score }
//    }
//
//    override suspend fun getRecents(): List<Material> {
//        return materials.filter {
//            it.user.username != UserInfo!!.username
//        }.sortedBy { it.postDate }
//    }
//}