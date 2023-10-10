package com.mindhub.model.api

import com.mindhub.model.entities.Badge

interface BadgeProvider {
    suspend fun getUnlockedBadges(xp: Int): List<Badge>
}

object BadgeFakeApi: BadgeProvider {
    private val badges = listOf(
        Badge("Aprendiz"),
        Badge("Expert"),
        Badge("Mestre")
    )

    override suspend fun getUnlockedBadges(xp: Int): List<Badge> {
        return badges
    }
}