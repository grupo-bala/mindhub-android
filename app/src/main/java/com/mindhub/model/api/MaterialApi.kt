package com.mindhub.model.api

import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.User
import kotlinx.serialization.Serializable

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
}

object MaterialFakeApi: MaterialProvider {
    private val materials = mutableListOf<Material>()
    private var count: Int = 0

    override suspend fun create(material: MaterialRequest): Material {
        count += 1
        val material = Material(
            id = count,
            title = material.title,
            content = material.content,
            expertise = material.expertise,
            user = material.user,
            score = 10
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

    override suspend fun get(title: String): List<Material> {
        return materials.filter { it.title.contains(title) }
    }
}