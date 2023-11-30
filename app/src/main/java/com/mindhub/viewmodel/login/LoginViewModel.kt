package com.mindhub.viewmodel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.LoginRequest
import com.mindhub.model.api.UserApi
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var feedback by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun login(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                isLoading = true
                feedback = ""
                UserApi.login(LoginRequest(email, password))
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            password = ""
            isLoading = false
        }
    }
}