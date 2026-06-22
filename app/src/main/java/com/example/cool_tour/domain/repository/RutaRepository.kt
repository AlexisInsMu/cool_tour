package com.example.cool_tour.domain.repository


import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.model.Ruta
import com.example.cool_tour.domain.model.RutaResumen
import kotlinx.coroutines.flow.Flow

interface RutaRepository {
    fun getRutas(): Flow<List<Ruta>>
    suspend fun getRutaById(id: String): Ruta?
    suspend fun guardarRuta(ruta: Ruta)
    suspend fun eliminarRuta(id: String)
    suspend fun guardarRutaEnBackend(nombre: String, pois: List<POI>): Result<Unit>
    suspend fun obtenerMisRutas(): Result<List<Ruta>>
    suspend fun obtenerRutasDisponibles(): Result<List<RutaResumen>>

    suspend fun obtenerRutaPorId(id: String): Result<Ruta>
}
