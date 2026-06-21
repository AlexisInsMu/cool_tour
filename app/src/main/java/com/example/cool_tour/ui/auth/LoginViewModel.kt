package com.example.cool_tour.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginEstado {
    object Idle : LoginEstado()
    object Cargando : LoginEstado()
    object Exito : LoginEstado()
    data class Error(val mensaje: String) : LoginEstado()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _estado = MutableLiveData<LoginEstado>(LoginEstado.Idle)
    val estado: LiveData<LoginEstado> = _estado

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _estado.value = LoginEstado.Error("Completa todos los campos")
            return
        }
        _estado.value = LoginEstado.Cargando
        viewModelScope.launch {
            authRepository.login(email, password)
                .onSuccess { _estado.value = LoginEstado.Exito }
                .onFailure { _estado.value = LoginEstado.Error("Email o contraseña incorrectos") }
        }
    }
}