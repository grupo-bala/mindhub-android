package com.mindhub.model.api

import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.LeaderboardEntry
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

interface RankingProvider {
    suspend fun getUserPosition(): Int
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}

object RankingApi : RankingProvider {
    override suspend fun getUserPosition(): Int {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ranking/rank/${CurrentUser.user!!.username}") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }

    override suspend fun getLeaderboard(): List<LeaderboardEntry> {
        val response: HttpResponse = Api.get("${BuildConfig.apiPrefix}/ranking") {
            header("Authorization", "Bearer ${CurrentUser.token}")
        }

        if (response.status != HttpStatusCode.OK) {
            println(response.body<ApiError>().message)
            throw Exception(response.body<ApiError>().message)
        }

        return response.body()
    }
}