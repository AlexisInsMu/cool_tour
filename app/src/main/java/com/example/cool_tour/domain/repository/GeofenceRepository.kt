package com.example.cool_tour.domain.repository

import com.example.cool_tour.domain.model.POI


interface GeofenceRepository {
    fun registrarGeofences(pois: List<POI>)
    fun limpiarGeofences()
}