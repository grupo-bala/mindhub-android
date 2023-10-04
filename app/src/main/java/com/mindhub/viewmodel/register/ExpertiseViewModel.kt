package com.mindhub.viewmodel.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.ExpertiseApi
import com.mindhub.model.api.RegisterRequest
import com.mindhub.model.api.UserApi
import com.mindhub.model.entities.Expertise
import kotlinx.coroutines.launch

class ExpertiseViewModel() : ViewModel() {
    var expertises = mutableStateListOf<Expertise>()
    var isLoading by mutableStateOf(false)
    var feedback by mutableStateOf("")

    private var isExpertisesLoaded = false
    private val selectedExpertises = mutableStateListOf<String>()

    fun toggleExpertise(expertise: String) {
        val valueInList = selectedExpertises.find { it == expertise }

        if (valueInList != null) {
            selectedExpertises.remove(expertise)
        } else if (selectedExpertises.size < 3) {
            selectedExpertises.add(expertise)
        }
    }

    fun isSelected(expertise: String): Boolean {
        return selectedExpertises.contains(expertise)
    }

    fun loadExpertises() {
        if (!isExpertisesLoaded) {
            viewModelScope.launch {
                try {
                    expertises.addAll(ExpertiseApi.getAllExpertises())
                } catch (_: Exception) { }
            }
        }
    }
    fun register(
        name: String,
        email: String,
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                val res = UserApi.register(RegisterRequest(name, email, username, password, selectedExpertises))
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }
}