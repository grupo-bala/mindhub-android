package com.mindhub.model.api

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.LatLng
import com.mindhub.BuildConfig
import com.mindhub.common.services.Config
import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Event
import com.mindhub.model.entities.User
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import java.time.LocalDateTime

interface EventProvider {
    suspend fun create(event: Event): Event
    suspend fun update(eventUpdated: Event)
    suspend fun remove(eventId: Int)
    suspend fun get(id: Int): Event
    suspend fun getForYou(page: Int): List<Event>
    suspend fun getRecents(page: Int): List<Event>
    suspend fun getUserEvents(username: String): List<Event>
}

object EventFakeApi : EventProvider {
    val events = mutableListOf<Event>().also {
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
        it.add(Event(IdManager.id++, user, "Teste 1", userScore = 0, "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Quixadá"))
        it.add(Event(IdManager.id++, user, "Teste 2", userScore = 0, "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Fortaleza"))
        it.add(Event(IdManager.id++, user, "Teste 3", userScore = 0, "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Quixeramobim"))
    }

    override suspend fun create(event: Event): Event {
        setLocalName(event)

        events.add(
            event.apply { this.id = IdManager.id++ }
        )

        return event
    }

    private fun setLocalName(event: Event) {
        val context = GeoApiContext.Builder()
            .apiKey(BuildConfig.apiKey)
            .build()

        val results = GeocodingApi.reverseGeocode(
            context,
            LatLng(event.latitude, event.longitude)
        ).await()

        val component = results[0].addressComponents.first {
            it.types.any { localType ->
                localType.name.lowercase() == "administrative_area_level_2"
            }
        }

        event.localName = component.longName
    }

    override suspend fun update(eventUpdated: Event) {
        val event = events.find { it.id == eventUpdated.id} ?: throw Exception()

        event.title = eventUpdated.title
        event.content = eventUpdated.content
        event.latitude = eventUpdated.latitude
        event.longitude = eventUpdated.longitude
        event.date = eventUpdated.date
        setLocalName(event)
    }

    override suspend fun remove(eventId: Int) {
        if(!events.removeIf { it.id == eventId }) {
            throw Exception()
        }
    }

    override suspend fun get(id: Int): Event {
        return events.find { it.id == id } ?: throw Exception()
    }

    override suspend fun getUserEvents(username: String): List<Event> {
        val filtered = events.filter {
            it.user.username == username
        }

        return filtered.reversed()
    }

    override suspend fun getForYou(page: Int): List<Event> {
        return events.filter {
            it.user.username != UserInfo!!.username
        }.sortedBy { it.score }
    }

    override suspend fun getRecents(page: Int): List<Event> {
        return events.filter {
            it.user.username != UserInfo!!.username
        }.sortedBy { it.postDate }
    }
}

object EventApi : EventProvider {
    private fun setLocalName(event: Event) {
        val context = GeoApiContext.Builder()
            .apiKey(BuildConfig.apiKey)
            .build()

        val results = GeocodingApi.reverseGeocode(
            context,
            LatLng(event.latitude, event.longitude)
        ).await()

        val component = results[0].addressComponents.first {
            it.types.any { localType ->
                localType.name.lowercase() == "administrative_area_level_2"
            }
        }

        event.localName = component.longName
    }

    override suspend fun create(event: Event): Event {
        setLocalName(event)

        val response: HttpResponse = Api.post("${Config.API_PREFIX}/events") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${UserInfo!!.token}")
            setBody(event)
        }

        if (response.status != HttpStatusCode.Created) {
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun update(eventUpdated: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun remove(eventId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): Event {
        TODO("Not yet implemented")
    }

    override suspend fun getForYou(page: Int): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecents(page: Int): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserEvents(username: String): List<Event> {
        TODO("Not yet implemented")
    }

}