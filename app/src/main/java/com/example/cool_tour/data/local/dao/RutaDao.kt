package com.example.cool_tour.data.local.dao

import androidx.room.*
import com.example.cool_tour.data.local.entity.RutaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaDao {
    @Query("SELECT * FROM rutas")
    fun getAllRutas(): Flow<List<RutaEntity>>

    @Query("SELECT * FROM rutas WHERE id = :id")
    suspend fun getRutaById(id: String): RutaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRuta(ruta: RutaEntity)

    @Query("DELETE FROM rutas WHERE id = :id")
    suspend fun deleteRutaById(id: String)
}