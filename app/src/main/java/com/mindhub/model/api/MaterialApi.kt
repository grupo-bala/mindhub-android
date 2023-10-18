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
    suspend fun create(material: MaterialRequest)
    suspend fun update(materialId: Int, materialUpdated: MaterialRequest)
    suspend fun getOne(id: Int): Material
    suspend fun get(title: String): List<Material>
}

object MaterialFakeApi: MaterialProvider {
    private val materials = mutableListOf<Material>()
    private var count: Int = 0

    override suspend fun create(material: MaterialRequest) {
        count += 1
        materials.add(Material(
            id = count,
            title = material.title,
            content = material.content,
            expertise = material.expertise,
            user = material.user
        ))
    }

    override suspend fun update(materialId: Int, materialUpdated: MaterialRequest) {
        val material = materials.find { it.id == materialId } ?: throw Exception()

        material.content = materialUpdated.content
        material.title = materialUpdated.title
        material.expertise = materialUpdated.expertise
    }

    override suspend fun getOne(id: Int): Material {
        return materials.find { it.id == id } ?: throw Exception()
    }

    override suspend fun get(title: String): List<Material> {
        return materials.filter { it.title.contains(title) }
    }
}