package com.example.cool_tour.data.local.dao

import androidx.room.*
import com.example.cool_tour.data.local.entity.POIEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface POIDao {
    @Query("SELECT * FROM pois")
    fun getAllPOIs(): Flow<List<POIEntity>>

    @Query("SELECT * FROM pois WHERE id = :id")
    suspend fun getPOIById(id: String): POIEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPOI(poi: POIEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPOIs(pois: List<POIEntity>)

    @Query("DELETE FROM pois WHERE id = :id")
    suspend fun deletePOIById(id: String)
}