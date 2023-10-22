package com.mindhub.model.api

import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User

interface AskProvider {
    suspend fun create(ask: Ask)
    suspend fun getOne(id: Int): Ask
    suspend fun get(title: String): List<Ask>
}

object AskFakeApi : AskProvider {
    val asks = mutableListOf<Ask>().also {
        val user = User("João", "joaum123@gmail.com", "jjaum", 0, Badge(""), listOf(), "")
        it.add(Ask(0, "Matemática 1", "teste", 76, user, Expertise("teste")))
    }

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