package com.example.cool_tour.data.local.dao

import androidx.room.*
import com.example.cool_tour.data.local.entity.AudioCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudio(audio: AudioCacheEntity)

    @Query("SELECT * FROM audio_cache WHERE poiId = :poiId")
    suspend fun getAudioPorPoi(poiId: String): AudioCacheEntity?

    @Query("SELECT * FROM audio_cache WHERE descargado = 1")
    fun getAudiosDescargados(): Flow<List<AudioCacheEntity>>

    @Query("DELETE FROM audio_cache WHERE poiId = :poiId")
    suspend fun deleteAudio(poiId: String)
}
