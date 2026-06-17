package com.example.cool_tour.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_cache")
data class AudioCacheEntity(
    @PrimaryKey val poiId: String,
    val rutaArchivoLocal: String,
    val descargado: Boolean = false,
    val fechaDescarga: Long = System.currentTimeMillis()
)