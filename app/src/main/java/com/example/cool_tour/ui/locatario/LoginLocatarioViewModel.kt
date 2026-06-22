package com.example.cool_tour.ui.locatario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.data.local.SessionManager
import com.example.cool_tour.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginLocatarioState {
    object Idle : LoginLocatarioState()
    object Cargando : LoginLocatarioState()
    object Exito : LoginLocatarioState()
    data class Error(val mensaje: String) : LoginLocatarioState()
}

@HiltViewModel
class LoginLocatarioViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _estado = MutableStateFlow<LoginLocatarioState>(LoginLocatarioState.Idle)
    val estado: StateFlow<LoginLocatarioState> = _estado

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _estado.value = LoginLocatarioState.Error("Email y password son requeridos")
            return
        }
        viewModelScope.launch {
            _estado.value = LoginLocatarioState.Cargando
            try {
                val response = apiService.loginLocatario(
                    mapOf("email" to email, "password" to password)
                )
                // suspend fun — se llama directamente desde viewModelScope
                sessionManager.guardarSesionLocatario(
                    token = response.token,
                    id = response.locatario.id,
                    nombre = response.locatario.nombre
                )
                _estado.value = LoginLocatarioState.Exito
            } catch (e: Exception) {
                _estado.value = LoginLocatarioState.Error("Credenciales inválidas")
            }
        }
    }
}