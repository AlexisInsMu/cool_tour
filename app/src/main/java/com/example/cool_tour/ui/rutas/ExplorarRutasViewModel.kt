package com.example.cool_tour.ui.rutas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.model.RutaResumen
import com.example.cool_tour.domain.repository.RutaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExplorarRutasViewModel @Inject constructor(
    private val rutaRepository: RutaRepository
) : ViewModel() {

    private val _rutas = MutableLiveData<List<RutaResumen>>()
    val rutas: LiveData<List<RutaResumen>> = _rutas

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    init {
        cargarRutasDisponibles()
    }

    private fun cargarRutasDisponibles() {
        viewModelScope.launch {
            _cargando.value = true
            rutaRepository.obtenerRutasDisponibles()
                .onSuccess { _rutas.value = it }
                .onFailure { _rutas.value = emptyList() }
            _cargando.value = false
        }
    }
}
