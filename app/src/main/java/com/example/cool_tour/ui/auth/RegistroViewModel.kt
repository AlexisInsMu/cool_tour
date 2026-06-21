package com.example.cool_tour.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _estado = MutableLiveData<LoginEstado>(LoginEstado.Idle)
    val estado: LiveData<LoginEstado> = _estado

    fun registrar(nombre: String, email: String, password: String) {
        if (nombre.isBlank() || email.isBlank()) {
            _estado.value = LoginEstado.Error("Completa todos los campos")
            return
        }

        if (!esPasswordValida(password)) {
            _estado.value = LoginEstado.Error("La contraseña debe tener 8+ caracteres, una mayúscula y un número")
            return
        }

        _estado.value = LoginEstado.Cargando
        viewModelScope.launch {
            authRepository.registrar(email, password, nombre)
                .onSuccess {
                    authRepository.login(email, password)
                        .onSuccess { _estado.value = LoginEstado.Exito }
                        .onFailure { _estado.value = LoginEstado.Error("Cuenta creada, inicia sesión") }
                }
                .onFailure { _estado.value = LoginEstado.Error("Este correo ya está registrado") }
        }
    }

    private fun esPasswordValida(password: String): Boolean {
        val tieneLongitudMinima = password.length >= 8
        val tieneMayuscula = password.any { it.isUpperCase() }
        val tieneNumero = password.any { it.isDigit() }
        return tieneLongitudMinima && tieneMayuscula && tieneNumero
    }
}