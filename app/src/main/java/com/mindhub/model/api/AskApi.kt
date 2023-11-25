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
    suspend fun getUserAsks(username: String): List<Ask>
}

object AskFakeApi : AskProvider {
    val asks = mutableListOf<Ask>().also {
        val user = User(
            "João",
            "joaum123@gmail.com",
            "jjaum",
            0,
            Badge("", 0),
            listOf(),
            listOf(),
            "",
            null
        )
        it.add(Ask(IdManager.id++, "Como aplicar o teorema de pitágoras em um círculo?", 0, "teste", 76, user, LocalDateTime.now(), Expertise("Matemática")))
        it.add(Ask(IdManager.id++, "A ligação metálica pode ser feita entre hidrogênio e sódio?", userScore = 0, "teste", 76, user, LocalDateTime.now(), Expertise("Química")))
        it.add(Ask(IdManager.id++, "Qual movimento literário foi introduzido no Brasil a partir da semana de 22", userScore = 0, "teste", 76, user, LocalDateTime.now(), Expertise("Literatura")))
    }

    override suspend fun create(ask: Ask): Ask {
        ask.id = IdManager.id++
        asks.add(ask)

        return ask
    }

    override suspend fun update(askUpdated: Ask) {
        val ask = asks.find { it.id == askUpdated.id } ?: throw Exception()

        ask.title = askUpdated.title
        ask.content = askUpdated.content
        ask.expertise = askUpdated.expertise
        ask.file = askUpdated.file
    }

    override suspend fun getOne(id: Int): Ask {
        return asks.find { it.id == id } ?: throw Exception()
    }

    override suspend fun get(title: String): List<Ask> {
        return asks.filter { it.title.contains(title) }
    }

    override suspend fun getForYou(page: Int): List<Ask> {
        val filtered = asks.filter {
            it.expertise in UserInfo!!.expertises && it.user.username != UserInfo!!.username
        }

        return filtered.sortedBy { it.score }
    }

    override suspend fun getRecents(page: Int): List<Ask> {
        return asks.filter {
            it.user.username != UserInfo!!.username
        }.sortedBy { it.postDate }
    }

    override suspend fun getUserAsks(username: String): List<Ask> {
        val filtered = asks.filter {
            it.user.username == username
        }

        return filtered.reversed()
    }

    override suspend fun remove(id: Int) {
        if (!asks.removeIf { it.id == id }) {
            throw Exception()
        }
    }
}