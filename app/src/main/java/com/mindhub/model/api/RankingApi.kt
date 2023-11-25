package com.mindhub.model.api

import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.LeaderboardEntry
import com.mindhub.model.entities.User

interface RankingProvider {
    suspend fun getUserPosition(): Int
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}

object RankingFakeApi: RankingProvider {
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

    override suspend fun getUserPosition(): Int {
        users.sortByDescending { it.xp }

        users.forEachIndexed { index, e ->
            if (UserInfo!!.username == e.username) {
                return index + 1
            }
        }

        return -1
    }

    override suspend fun getLeaderboard(): List<LeaderboardEntry> {
        users.sortByDescending { it.xp }

        val leaderboardEntries = mutableListOf<LeaderboardEntry>()

        for (user in  users) {
            leaderboardEntries.add(LeaderboardEntry(user.username, user.xp))
        }

        return leaderboardEntries
    }
}