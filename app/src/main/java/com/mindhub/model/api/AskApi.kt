package com.mindhub.model.api

import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User

interface AskProvider {
    suspend fun create(ask: Ask): Ask
    suspend fun update(askUpdated: Ask)
    suspend fun remove(id: Int)
    suspend fun getOne(id: Int): Ask
    suspend fun get(title: String): List<Ask>
}

object AskFakeApi : AskProvider {
    val asks = mutableListOf<Ask>().also {
        val user = User("João", "joaum123@gmail.com", "jjaum", 0, Badge(""), listOf(), "")
        it.add(Ask(0, "Matemática 1", "teste", 76, user, Expertise("teste")))
    }

    override suspend fun create(ask: Ask): Ask {
        asks.add(ask)
        return ask
    }

    override suspend fun update(askUpdated: Ask) {
        val ask = asks.find { it.id == askUpdated.id } ?: throw Exception()

        ask.title = askUpdated.title
        ask.content = askUpdated.content
        ask.expertise = askUpdated.expertise
    }

    override suspend fun getOne(id: Int): Ask {
        return asks.find { it.id == id } ?: throw Exception()
    }

    override suspend fun get(title: String): List<Ask> {
        return asks.filter { it.title.contains(title) }
    }

    override suspend fun remove(id: Int) {
        if (!asks.removeIf { it.id == id }) {
            throw Exception()
        }
    }
}