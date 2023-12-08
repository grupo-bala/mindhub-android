package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.ErrorParser
import com.mindhub.model.api.RankingApi
import com.mindhub.model.entities.LeaderboardEntry
import kotlinx.coroutines.launch

class RankingViewModel() : ViewModel() {
    var userPositionInRank by mutableIntStateOf(0)
    var leaderboardEntries = mutableStateListOf<LeaderboardEntry>()
    var isLoading by mutableStateOf(false)
    var feedback by mutableStateOf("")
    var hasLoaded by mutableStateOf(false)

    fun loadLeaderBoard() {
        if (hasLoaded) {
            return
        }

        viewModelScope.launch {
            try {
                feedback = ""
                isLoading = true

                userPositionInRank = RankingApi.getUserPosition()
                leaderboardEntries.addAll(RankingApi.getLeaderboard())
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)
            }

            isLoading = false
            hasLoaded = true
        }
    }
}
