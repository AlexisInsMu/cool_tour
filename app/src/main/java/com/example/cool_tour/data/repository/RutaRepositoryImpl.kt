package com.example.cool_tour.data.repository

import com.example.cool_tour.data.local.dao.RutaDao
import com.example.cool_tour.data.mapper.toDomain
import com.example.cool_tour.data.mapper.toEntity
import com.example.cool_tour.domain.model.Ruta
import com.example.cool_tour.domain.repository.RutaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RutaRepositoryImpl @Inject constructor(
    private val rutaDao: RutaDao
) : RutaRepository {

    override fun getRutas(): Flow<List<Ruta>> =
        rutaDao.getAllRutas().map { list -> list.map { it.toDomain() } }

    override suspend fun getRutaById(id: String): Ruta? =
        rutaDao.getRutaById(id)?.toDomain()

    override suspend fun guardarRuta(ruta: Ruta) =
        rutaDao.insertRuta(ruta.toEntity())

    override suspend fun eliminarRuta(id: String) =
        rutaDao.deleteRutaById(id)
}