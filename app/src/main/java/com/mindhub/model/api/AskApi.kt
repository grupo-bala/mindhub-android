package com.mindhub.model.api

import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import java.time.LocalDateTime

interface AskProvider {
    suspend fun create(ask: Ask): Ask
    suspend fun update(askUpdated: Ask)
    suspend fun remove(id: Int)
    suspend fun getOne(id: Int): Ask
    suspend fun get(title: String): List<Ask>
    suspend fun getForYou(page: Int): List<Ask>
    suspend fun getRecents(page: Int): List<Ask>
}

object AskFakeApi : AskProvider {
    private var count = 0

    val asks = mutableListOf<Ask>().also {
        val user = User("João", "joaum123@gmail.com", "jjaum", 0, Badge(""), listOf(), "")
        it.add(Ask(count++, "Matemática 1", "teste", 76, user, LocalDateTime.now(), Expertise("Matemática")))
        it.add(Ask(count++, "Química 1", "teste", 76, user, LocalDateTime.now(), Expertise("Química")))
        it.add(Ask(count++, "Literatura 1", "teste", 76, user, LocalDateTime.now(), Expertise("Literatura")))
    }

    override suspend fun create(ask: Ask): Ask {
        count += 1
        ask.id = count

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

    override suspend fun getForYou(page: Int): List<Ask> {
        val filtered = asks.filter { it.expertise in UserInfo!!.expertises }
        return filtered.sortedBy { it.score }
    }

    override suspend fun getRecents(page: Int): List<Ask> {
        return asks.sortedBy { it.date }
    }

    override suspend fun remove(id: Int) {
        if (!asks.removeIf { it.id == id }) {
            throw Exception()
        }
    }
}