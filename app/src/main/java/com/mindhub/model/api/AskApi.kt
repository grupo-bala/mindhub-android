package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Ask
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

interface AskProvider {
    suspend fun create(ask: AskRequest, img: ByteArray?): Ask
    suspend fun update(askUpdated: AskRequest, askId: Int, img: ByteArray?)
    suspend fun remove(askId: Int)
    suspend fun getOne(askId: Int): Ask
    suspend fun get(askTitle: String): List<Ask>
    suspend fun getForYou(): List<Ask>
    suspend fun getRecents(): List<Ask>
    suspend fun getUserAsks(username: String): List<Ask>
}

@Serializable
data class AskRequest(
    val title: String,
    val content: String,
    val expertise: String,
    val postDate: String,
    val hasImage: Boolean
)

object AskApi: AskProvider {
    override suspend fun create(ask: AskRequest, img: ByteArray?): Ask {
        val response: HttpResponse = Api.post("${BuildConfig.apiPrefix}/ask") {
            contentType(ContentType.Application.Json)
            setBody(ask)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.Created) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        val createdAsk = response.body<Ask>()

        if (img != null) {
            FileApi.upload("ask", createdAsk.id, img)
        }

        return createdAsk
    }

    override suspend fun update(askUpdated: AskRequest, askId: Int, img: ByteArray?) {
        val response: HttpResponse = Api.patch("${BuildConfig.apiPrefix}/ask/$askId") {
            contentType(ContentType.Application.Json)
            setBody(askUpdated)
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        if (img != null) {
            FileApi.upload("ask", askId, img)

        }

        return response.body()
    }

    override suspend fun remove(askId: Int) {
        val response: HttpResponse = Api.delete("${BuildConfig.apiPrefix}/ask/$askId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getOne(askId: Int): Ask {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/id/$askId") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun get(askTitle: String): List<Ask> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/$askTitle") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getForYou(): List<Ask> {
        return listOf()
    }

    override suspend fun getRecents(): List<Ask> {
        return listOf()
    }

    override suspend fun getUserAsks(username: String): List<Ask> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ask/user/${username}") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

}

//object AskFakeApi : AskProvider {
//    val asks = mutableListOf<Ask>().also {
//        val user = User(
//            "João",
//            "joaum123@gmail.com",
//            "jjaum",
//            0,
//            Badge("", 0),
//            listOf(),
//            listOf(),
//            null,
//        )
//        it.add(Ask(IdManager.id++, "Como aplicar o teorema de pitágoras em um círculo?", 0, "teste", 76, user, LocalDateTime.now(), Expertise("Matemática")))
//        it.add(Ask(IdManager.id++, "A ligação metálica pode ser feita entre hidrogênio e sódio?", userScore = 0, "teste", 76, user, LocalDateTime.now(), Expertise("Química")))
//        it.add(Ask(IdManager.id++, "Qual movimento literário foi introduzido no Brasil a partir da semana de 22", userScore = 0, "teste", 76, user, LocalDateTime.now(), Expertise("Literatura")))
//    }
//
//    override suspend fun create(ask: Ask, tempImage: Bitmap?): Ask {
//        ask.id = IdManager.id++
//        asks.add(ask)
//
//        return ask
//    }
//
//    override suspend fun update(askUpdated: Ask) {
//        val ask = asks.find { it.id == askUpdated.id } ?: throw Exception()
//
//        ask.title = askUpdated.title
//        ask.content = askUpdated.content
//        ask.expertise = askUpdated.expertise
//        ask.image = askUpdated.image
//    }
//
//    override suspend fun getOne(id: Int): Ask {
//        return asks.find { it.id == id } ?: throw Exception()
//    }
//
//    override suspend fun get(title: String): List<Ask> {
//        return asks.filter { it.title.contains(title) }
//    }
//
//    override suspend fun getForYou(page: Int): List<Ask> {
//        val filtered = asks.filter {
//            it.expertise in CurrentUser.user!!.expertises && it.user.username != CurrentUser.user!!.username
//        }
//
//        return filtered.sortedBy { it.score }
//    }
//
//    override suspend fun getRecents(page: Int): List<Ask> {
//        return asks.filter {
//            it.user.username != CurrentUser.user!!.username
//        }.sortedBy { it.postDate }
//    }
//
//    override suspend fun getUserAsks(username: String): List<Ask> {
//        val filtered = asks.filter {
//            it.user.username == username
//        }
//
//        return filtered.reversed()
//    }
//
//    override suspend fun remove(id: Int) {
//        if (!asks.removeIf { it.id == id }) {
//            throw Exception()
//        }
//    }
//}

