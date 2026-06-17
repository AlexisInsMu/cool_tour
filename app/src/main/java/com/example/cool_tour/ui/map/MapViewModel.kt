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
    private val calcularRutaUseCase: CalcularRutaUseCase
) : ViewModel() {

    private val _pois = MutableLiveData<List<POI>>()
    val pois: LiveData<List<POI>> = _pois

    private val _rutaPolilinea = MutableLiveData<PolylineOptions?>()
    val rutaPolilinea: LiveData<PolylineOptions?> = _rutaPolilinea

    fun cargarPOIs() {
        viewModelScope.launch {
            poiRepository.getPOIs().collect { lista ->
                if (lista.isEmpty()) cargarPOIsStub() else _pois.value = lista
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
        }
    }

    private suspend fun cargarPOIsStub() {
        val stubs = listOf(
            POI("P1", "Catedral Central", "Catedral histórica del centro", 19.4326, -99.1332, "", ""),
            POI("P2", "Museo Nacional", "Arte e historia de México", 19.4260, -99.1455, "", ""),
            POI("P3", "Jardín Principal", "El parque más antiguo", 19.4350, -99.1400, "", "")
        )
        poiRepository.insertPOIs(stubs)
        _pois.value = stubs
    }
}
