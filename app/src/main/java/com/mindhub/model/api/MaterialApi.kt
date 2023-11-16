package com.mindhub.model.api

import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.User
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MaterialRequest(
    val user: User,
    val title: String,
    val content: String,
    val expertise: Expertise,
)

interface MaterialProvider {
    suspend fun create(material: MaterialRequest): Material
    suspend fun update(materialUpdated: Material)
    suspend fun remove(materialId: Int)
    suspend fun getOne(id: Int): Material
    suspend fun get(title: String): List<Material>
    suspend fun getForYou(page: Int): List<Material>
    suspend fun getRecents(page: Int): List<Material>
    suspend fun getUserMaterials(username: String): List<Material>
}

object MaterialFakeApi: MaterialProvider {
    private var count: Int = 0
    private val materials = mutableListOf<Material>().also {
        val user = User("João", "joaum123@gmail.com", "jjaum", 0, Badge(""), listOf(), "")
        it.add(Material(count++, user, "Relações trigonométricas em 1 minuto!", userScore = 0, "teste", 87, LocalDateTime.now(), Expertise("Matemática")))
        it.add(Material(count++, user, "As três leis de Newton com exemplos", userScore = 0, "teste", 87, LocalDateTime.now(), Expertise("Física")))
        it.add(Material(count++, user, "Os biomas brasileiros explicados", userScore = 0, "teste", 87, LocalDateTime.now(), Expertise("Geografia")))
    }

    override suspend fun create(material: MaterialRequest): Material {
        count += 1
        val material = Material(
            id = count,
            title = material.title,
            content = material.content,
            expertise = material.expertise,
            user = material.user,
            postDate = LocalDateTime.now(),
            userScore = 0,
            score = 0
        )

        materials.add(material)

        return material
    }

    override suspend fun update(materialUpdated: Material) {
        val material = materials.find { it.id == materialUpdated.id } ?: throw Exception()

        material.content = materialUpdated.content
        material.title = materialUpdated.title
        material.expertise = materialUpdated.expertise
    }

    override suspend fun remove(materialId: Int) {
        if (!materials.removeIf { it.id == materialId }) {
            throw Exception()
        }
    }

    override suspend fun getOne(id: Int): Material {
        return materials.find { it.id == id } ?: throw Exception()
    }

    override suspend fun getUserMaterials(username: String): List<Material> {
        val filtered = materials.filter {
            it.user.username == username
        }

        return filtered.reversed()
    }

    override suspend fun get(title: String): List<Material> {
        return materials.filter { it.title.contains(title) }
    }

    override suspend fun getForYou(page: Int): List<Material> {
        val filtered = materials.filter {
            it.expertise in UserInfo!!.expertises && it.user.username != UserInfo!!.username
        }

        return filtered.sortedBy { it.score }
    }

    override suspend fun getRecents(page: Int): List<Material> {
        return materials.filter {
            it.user.username != UserInfo!!.username
        }.sortedBy { it.postDate }
    }
}