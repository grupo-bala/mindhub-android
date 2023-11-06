package com.mindhub.model.api

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.LatLng
import com.mindhub.BuildConfig
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Event
import com.mindhub.model.entities.User
import java.time.LocalDateTime


interface EventProvider {
    suspend fun create(event: Event): Event
    suspend fun get(id: Int): Event
    suspend fun getForYou(page: Int): List<Event>
    suspend fun getRecents(page: Int): List<Event>
}

object EventFakeApi : EventProvider {
    private var count = 0

    val events = mutableListOf<Event>().also {
        val user = User("João", "joaum123@gmail.com", "jjaum", 0, Badge(""), listOf(), "")
        it.add(Event(count++, user, "Teste 1", "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Quixadá"))
        it.add(Event(count++, user, "Teste 2", "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Fortaleza"))
        it.add(Event(count++, user, "Teste 3", "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Quixeramobim"))
    }

    override suspend fun create(event: Event): Event {
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

        events.add(
            event.apply { this.id = count++ }
        )

        return event
    }

    override suspend fun get(id: Int): Event {
        return events.find { it.id == id } ?: throw Exception()
    }

    override suspend fun getForYou(page: Int): List<Event> {
        return events.sortedBy { it.score }
    }

    override suspend fun getRecents(page: Int): List<Event> {
        return events.sortedBy { it.postDate }
    }
}