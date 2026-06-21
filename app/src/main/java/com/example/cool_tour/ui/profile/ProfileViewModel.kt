package com.example.cool_tour.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.model.Ruta
import com.example.cool_tour.domain.repository.RutaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val rutaRepository: RutaRepository
) : ViewModel() {

    private val _historialRutas = MutableLiveData<List<Ruta>>()
    val historialRutas: LiveData<List<Ruta>> = _historialRutas

    init {
        cargarHistorial()
    }

    private fun cargarHistorial() {
        viewModelScope.launch {
            rutaRepository.obtenerMisRutas()
                .onSuccess { _historialRutas.value = it }
                .onFailure { _historialRutas.value = emptyList() }
        }
    }
}