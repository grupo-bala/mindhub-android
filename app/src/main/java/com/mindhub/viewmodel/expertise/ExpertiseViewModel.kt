package com.mindhub.viewmodel.expertise

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.ExpertiseFakeApi
import com.mindhub.model.entities.Expertise
import com.mindhub.common.services.UserInfo
import kotlinx.coroutines.launch

class ExpertiseViewModel() : ViewModel() {
    var expertises = mutableStateListOf<Expertise>()
    var selectedExpertises = mutableStateListOf<Expertise>()

    private var isExpertisesLoaded = false

    fun loadExpertises() {
        if (!isExpertisesLoaded) {
            for (expertise in UserInfo?.expertises ?: listOf()) {
                selectedExpertises.add(expertise)
            }

            viewModelScope.launch {
                try {
                    expertises.addAll(ExpertiseFakeApi.getAllExpertises())
                } catch (_: Exception) { }
            }

            isExpertisesLoaded = true
        }
    }

    fun toggleExpertise(expertise: Expertise) {
        val valueInList = selectedExpertises.find { it == expertise }

        if (valueInList == null && selectedExpertises.size < 3) {
            selectedExpertises.add(expertise)
        } else if (valueInList != null && selectedExpertises.size > 1) {
            selectedExpertises.remove(expertise)
        }
    }

    fun isSelected(expertise: Expertise): Boolean {
        return selectedExpertises.contains(expertise)
    }

    fun isFilled(): Boolean {
        return selectedExpertises.size > 0
    }
}