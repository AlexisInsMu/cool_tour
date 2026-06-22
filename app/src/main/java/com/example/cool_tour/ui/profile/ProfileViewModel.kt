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
    private val _rutasPorVisitar = MutableLiveData<List<Ruta>>()
    val rutasPorVisitar: LiveData<List<Ruta>> = _rutasPorVisitar

    init {
        cargarHistorial()
    }

    private fun cargarHistorial() {
        viewModelScope.launch {
            rutaRepository.obtenerMisRutas()
                .onSuccess { rutas ->
                    // Por ahora mostramos todas en historial
                    // hasta implementar tracking de estado por usuario
                    _historialRutas.value = rutas
                    _rutasPorVisitar.value = emptyList()
                }
                .onFailure {
                    _historialRutas.value = emptyList()
                    _rutasPorVisitar.value = emptyList()
                }
        }
    }
}