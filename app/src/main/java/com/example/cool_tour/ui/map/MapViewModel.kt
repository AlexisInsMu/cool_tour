package com.example.cool_tour.ui.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.repository.LocationRepository
import com.example.cool_tour.domain.repository.POIRepository
import com.example.cool_tour.domain.usecase.CalcularRutaUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val poiRepository: POIRepository,
    private val locationRepository: LocationRepository,
    private val calcularRutaUseCase: CalcularRutaUseCase,
    private val geofenceRepository: com.example.cool_tour.domain.repository.GeofenceRepository
) : ViewModel() {

    private val _pois = MutableLiveData<List<POI>>()
    val pois: LiveData<List<POI>> = _pois



    private val _rutaPolilinea = MutableLiveData<PolylineOptions?>()
    val rutaPolilinea : LiveData<PolylineOptions?> = _rutaPolilinea
    private val _ubicacionActual = MutableLiveData<android.location.Location?>()
    val ubicacionActual: LiveData<android.location.Location?> = _ubicacionActual

    // DESPUÉS — pública y lanzada en coroutine
    fun cargarPOIs() {
        viewModelScope.launch {
            poiRepository.sincronizarDesdeBackend()   // ← Trae del backend primero
            poiRepository.getPOIs().collect { lista ->
                _pois.value = lista   // ← Ya no hay fallback a stub
            }
        }
    }
    fun iniciarSeguimientoUbicacion() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation().collect { location ->
                _ubicacionActual.value = location
            }
        }
    }


    fun trazarRuta(pois: List<POI>) {
        viewModelScope.launch {
            val ruta = calcularRutaUseCase(pois)
            val opciones = PolylineOptions().apply {
                ruta.pois.forEach { add(LatLng(it.latitud, it.longitud)) }
                width(8f)
                color(android.graphics.Color.BLUE)
            }
            _rutaPolilinea.value = opciones
            // Registrar geofences automáticamente al iniciar ruta
            geofenceRepository.registrarGeofences(pois)
        }
    }
}
