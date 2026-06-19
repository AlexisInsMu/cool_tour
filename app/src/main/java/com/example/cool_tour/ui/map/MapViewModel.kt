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
            val stubs = listOf(
                POI("P1", "Catedral Central", "Catedral histórica del centro", 19.4326, -99.1332,
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", ""),
                POI("P2", "Museo Nacional", "Arte e historia de México", 19.4260, -99.1455,
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", ""),
                POI("P3", "Jardín Principal", "El parque más antiguo", 19.4350, -99.1400,
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", ""),
                POI("P4", "Mercado de Artesanías", "Mercado tradicional con 200 locales", 19.4280, -99.1380,
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3", ""),
                POI("P5", "Mirador del Cerro", "Vista panorámica desde 2800 metros", 19.4400, -99.1500,
                    "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3", "")
            )
            poiRepository.insertPOIs(stubs)
            _pois.postValue(stubs)   // postValue en vez de .value porque estamos en coroutine
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
