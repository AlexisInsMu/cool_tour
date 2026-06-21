package com.example.cool_tour.domain.repository


import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.model.Ruta
import kotlinx.coroutines.flow.Flow

interface RutaRepository {
    fun getRutas(): Flow<List<Ruta>>
    suspend fun getRutaById(id: String): Ruta?
    suspend fun guardarRuta(ruta: Ruta)
    suspend fun eliminarRuta(id: String)
    suspend fun guardarRutaEnBackend(nombre: String, pois: List<POI>): Result<Unit>
    suspend fun obtenerMisRutas(): Result<List<Ruta>>
}