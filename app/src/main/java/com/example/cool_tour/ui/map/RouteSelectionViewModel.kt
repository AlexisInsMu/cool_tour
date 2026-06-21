package com.example.cool_tour.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.model.Ruta
import com.example.cool_tour.domain.repository.POIRepository
import com.example.cool_tour.domain.repository.RutaRepository
import com.example.cool_tour.domain.usecase.CalcularRutaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteSelectionViewModel @Inject constructor(
    private val poiRepository: POIRepository,
    private val rutaRepository: RutaRepository,
    private val calcularRutaUseCase: CalcularRutaUseCase
) : ViewModel() {

    private val _pois = MutableLiveData<List<POI>>()
    val pois: LiveData<List<POI>> = _pois

    private val _guardadoExitoso = MutableLiveData<Boolean>()
    val guardadoExitoso: LiveData<Boolean> = _guardadoExitoso

    private val seleccionados = mutableSetOf<POI>()

    init {
        viewModelScope.launch {
            poiRepository.getPOIs().collect { _pois.value = it }
        }
    }

    fun togglePOI(poi: POI, agregar: Boolean) {
        if (agregar) seleccionados.add(poi) else seleccionados.remove(poi)
    }

    fun generarRutaLibre(): Ruta? {
        if (seleccionados.isEmpty()) return null
        val ruta = calcularRutaUseCase(seleccionados.toList())

        // ← Guardar en backend
        viewModelScope.launch {
            rutaRepository.guardarRutaEnBackend(ruta.nombre, seleccionados.toList())
                .onSuccess { _guardadoExitoso.value = true }
                .onFailure { _guardadoExitoso.value = false }
        }

        return ruta
    }
}