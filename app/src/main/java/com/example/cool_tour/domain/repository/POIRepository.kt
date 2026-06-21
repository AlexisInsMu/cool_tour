package com.example.cool_tour.domain.repository

import com.example.cool_tour.domain.model.POI
import kotlinx.coroutines.flow.Flow

interface POIRepository {
    fun getPOIs(): Flow<List<POI>>
    suspend fun getPOIById(id: String): POI?
    suspend fun insertPOI(poi: POI)
    suspend fun insertPOIs(pois: List<POI>)
    suspend fun sincronizarDesdeBackend()
}