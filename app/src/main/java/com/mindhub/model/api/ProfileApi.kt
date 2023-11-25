package com.mindhub.model.api

import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User

interface ProfileProvider {
    suspend fun getUserInformation(username: String): User
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
                ),
                token = ""
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
                ),
                token = ""
            )
        )
    }

    override suspend fun getUserInformation(username: String): User {
        return users.find { it.username == username }!!
    }
}