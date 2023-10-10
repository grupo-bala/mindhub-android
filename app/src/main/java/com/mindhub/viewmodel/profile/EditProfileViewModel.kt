package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.ExpertiseApi
import com.mindhub.model.api.UpdateRequest
import com.mindhub.model.api.UserFakeApi
import com.mindhub.model.entities.Expertise
import com.mindhub.services.UserInfo
import kotlinx.coroutines.launch

class EditProfileViewModel() : ViewModel() {
    var name by mutableStateOf(UserInfo!!.name)
    var email by mutableStateOf(UserInfo!!.email)
    var selectedExpertises = mutableStateListOf<Expertise>()
    var badge by mutableStateOf(UserInfo!!.currentBadge)
    var badges = mutableStateListOf<String>()
    var isLoading by mutableStateOf(false)

    var expertises = mutableStateListOf<Expertise>()

    private var isBadgesLoaded = false
    private var isExpertisesLoaded = false

    fun loadBadges() {
        if (!isBadgesLoaded) {
            // TODO: load achievements based on the API

            badges.addAll(listOf("Aprendiz", "Experiente", "Mestre"))

            isBadgesLoaded = true
        }
    }

    fun isSelected(expertise: String): Boolean {
        return selectedExpertises.contains(Expertise(expertise))
    }

    fun toggleExpertise(expertise: Expertise) {
        val valueInList = selectedExpertises.find { it == expertise }

        if (valueInList == null && selectedExpertises.size < 3) {
            selectedExpertises.add(expertise)
        } else if (valueInList != null && selectedExpertises.size > 1) {
            selectedExpertises.remove(expertise)
        }
    }

    fun loadExpertises() {
        if (!isExpertisesLoaded) {
            for (expertise in UserInfo!!.expertises) {
                selectedExpertises.add(expertise)
            }

            viewModelScope.launch {
                try {
                    expertises.addAll(ExpertiseApi.getAllExpertises())
                } catch (_: Exception) { }
            }

            isExpertisesLoaded = true
        }
    }

    fun update(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true

                val res = UserFakeApi.update(UpdateRequest(name, email, selectedExpertises))

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }
}
