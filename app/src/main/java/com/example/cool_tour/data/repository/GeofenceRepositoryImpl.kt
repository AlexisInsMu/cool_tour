package com.example.cool_tour.data.repository

import com.example.cool_tour.data.location.GeofenceManager
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.repository.GeofenceRepository
import javax.inject.Inject

class GeofenceRepositoryImpl @Inject constructor(
    private val geofenceManager: GeofenceManager
) : GeofenceRepository {

    override fun registrarGeofences(pois: List<POI>) {
        geofenceManager.registrar(pois)
    }

    override fun limpiarGeofences() {
        geofenceManager.limpiar()
    }
}