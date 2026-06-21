package com.example.cool_tour.data.repository

import com.example.cool_tour.data.local.dao.POIDao
import com.example.cool_tour.data.mapper.toDomain
import com.example.cool_tour.data.mapper.toEntity
import com.example.cool_tour.data.remote.ApiService
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.repository.POIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class POIRepositoryImpl @Inject constructor(
    private val poiDao: POIDao,
    private val apiService: ApiService
) : POIRepository {

    override fun getPOIs(): Flow<List<POI>> =
        poiDao.getAllPOIs().map { list -> list.map { it.toDomain() } }

    override suspend fun getPOIById(id: String): POI? =
        poiDao.getPOIById(id)?.toDomain()

    override suspend fun insertPOI(poi: POI) =
        poiDao.insertPOI(poi.toEntity())

    override suspend fun insertPOIs(pois: List<POI>) =
        poiDao.insertPOIs(pois.map { it.toEntity() })

    suspend fun sincronizarDesdeBackend() {
        try {
            val poisRemotos = apiService.getPOIs().map { it.toDomain() }
            insertPOIs(poisRemotos)
        } catch (e: Exception) {
            // Sin conexión: Room sigue sirviendo lo que ya tenía cacheado
        }
    }
}