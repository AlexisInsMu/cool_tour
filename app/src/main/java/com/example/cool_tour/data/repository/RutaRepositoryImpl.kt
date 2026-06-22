package com.example.cool_tour.data.repository

import com.example.cool_tour.data.local.SessionManager
import com.example.cool_tour.data.local.dao.RutaDao
import com.example.cool_tour.data.mapper.toDomain
import com.example.cool_tour.data.mapper.toEntity
import com.example.cool_tour.data.remote.ApiService
import com.example.cool_tour.data.remote.dto.RutaRequest
import com.example.cool_tour.domain.model.RutaResumen
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.model.Ruta
import com.example.cool_tour.domain.repository.RutaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
class RutaRepositoryImpl @Inject constructor(
    private val rutaDao: RutaDao,
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : RutaRepository {

    override fun getRutas(): Flow<List<Ruta>> =
        rutaDao.getAllRutas().map { list -> list.map { it.toDomain() } }

    override suspend fun getRutaById(id: String): Ruta? =
        rutaDao.getRutaById(id)?.toDomain()

    override suspend fun guardarRuta(ruta: Ruta) =
        rutaDao.insertRuta(ruta.toEntity())

    override suspend fun eliminarRuta(id: String) =
        rutaDao.deleteRutaById(id)

    override suspend fun guardarRutaEnBackend(nombre: String, pois: List<POI>): Result<Unit> {
        return try {
            val token = sessionManager.obtenerToken() ?: return Result.failure(Exception("No autenticado"))
            apiService.crearRuta(
                token = "Bearer $token",
                body = RutaRequest(nombre = nombre, poisIds = pois.map { it.id })
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerMisRutas(): Result<List<Ruta>> {
        return try {
            val token = sessionManager.obtenerToken() ?: return Result.failure(Exception("No autenticado"))
            val rutasRemotas = apiService.misRutas("Bearer $token")
            val rutas = rutasRemotas.map { dto ->
                Ruta(
                    id = dto.id,
                    nombre = dto.nombre,
                    descripcion = dto.estado,
                    pois = dto.pois.map { it.poi.let { p ->
                        POI(p.id, p.nombre, p.descripcion, p.latitud, p.longitud, p.audioUrl, p.imageUrl ?: "", p.radioMetros)
                    }},
                    distanciaKm = 0.0
                )
            }
            Result.success(rutas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun obtenerRutasDisponibles(): Result<List<RutaResumen>> {
        return try {
            val response = apiService.getRutasExplorar() // método de tu interfaz Retrofit
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerRutaPorId(id: String): Result<Ruta> {
        return try {
            val token = sessionManager.obtenerToken()
                ?: return Result.failure(Exception("No autenticado"))
            val dto = apiService.getRutaDetalle("Bearer $token", id)
            val pois = dto.pois.map { rutaPoiDto ->
                POI(
                    id = rutaPoiDto.poi.id,
                    nombre = rutaPoiDto.poi.nombre,
                    descripcion = rutaPoiDto.poi.descripcion,
                    latitud = rutaPoiDto.poi.latitud,
                    longitud = rutaPoiDto.poi.longitud,
                    audioUrl = rutaPoiDto.poi.audioUrl,
                    imageUrl = rutaPoiDto.poi.imageUrl ?: "",
                    radioMetros = rutaPoiDto.poi.radioMetros
                )
            }
            Result.success(
                Ruta(
                    id = dto.id,
                    nombre = dto.nombre,
                    descripcion = dto.descripcion ?: "",
                    pois = pois,
                    distanciaKm = 0.0
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}