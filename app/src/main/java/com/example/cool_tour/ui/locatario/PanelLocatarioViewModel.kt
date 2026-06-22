package com.example.cool_tour.ui.locatario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.data.local.SessionManager
import com.example.cool_tour.data.remote.ApiService
import com.example.cool_tour.domain.model.POI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PanelLocatarioViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _pois = MutableStateFlow<List<POI>>(emptyList())
    val pois: StateFlow<List<POI>> = _pois

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init { cargarMisPOIs() }

    fun cargarMisPOIs() {
        viewModelScope.launch {
            try {
                // suspend fun — se llama directamente desde viewModelScope
                val token = sessionManager.obtenerTokenLocatario() ?: return@launch
                val dtos = apiService.getMisPOIs("Bearer $token")
                _pois.value = dtos.map {
                    POI(it.id, it.nombre, it.descripcion, it.latitud, it.longitud,
                        it.audioUrl, it.imageUrl ?: "", it.radioMetros)
                }
            } catch (e: Exception) {
                _error.value = "No se pudieron cargar tus puntos"
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            // suspend fun — necesita coroutine
            sessionManager.cerrarSesionLocatario()
        }
    }
}