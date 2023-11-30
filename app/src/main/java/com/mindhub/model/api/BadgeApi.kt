package com.mindhub.model.api

import com.mindhub.model.entities.Badge

interface BadgeProvider {
    suspend fun getUnlockedBadges(xp: Int): List<Badge>
}

object BadgeFakeApi: BadgeProvider {
    private val badges = listOf(
        Badge("Aprendiz", 0, 0),
        Badge("Expert", 1, 0),
        Badge("Mestre", 2, 0)
    )

    override suspend fun getUnlockedBadges(xp: Int): List<Badge> {
        return badges
    }
}