package com.mindhub.model.api

import com.mindhub.model.entities.Ask

interface AskProvider {
    suspend fun create(ask: Ask)
    suspend fun getOne(id: Int): Ask
    suspend fun get(title: String): List<Ask>
}

object AskFakeApi : AskProvider {
    private val asks = mutableListOf<Ask>()
    override suspend fun create(ask: Ask) {
        asks.add(ask)
    }

    override suspend fun getOne(id: Int): Ask {
        return asks.find { it.id == id } ?: throw Exception()
    }

    override suspend fun get(title: String): List<Ask> {
        return asks.filter { it.title.contains(title) }
    }
}