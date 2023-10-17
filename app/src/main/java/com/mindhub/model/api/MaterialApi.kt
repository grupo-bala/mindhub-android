package com.mindhub.model.api

import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.User

data class MaterialCreateRequest(
    val user: User,
    val title: String,
    val content: String,
    val expertise: Expertise,
)

interface MaterialProvider {
    suspend fun create(material: MaterialCreateRequest)
    suspend fun getOne(id: Int): Material
    suspend fun get(title: String): List<Material>
}

object MaterialFakeApi: MaterialProvider {
    private val materials = mutableListOf<Material>()
    private var count: Int = 0

    override suspend fun create(material: MaterialCreateRequest) {
        count += 1
        materials.add(Material(
            id = count,
            title = material.title,
            content = material.content,
            expertise = material.expertise,
            user = material.user
        ))
    }

    override suspend fun getOne(id: Int): Material {
        return materials.find { it.id == id } ?: throw Exception()
    }

    override suspend fun get(title: String): List<Material> {
        return materials.filter { it.title.contains(title) }
    }
}