package com.example.cool_tour.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cool_tour.data.local.dao.AudioCacheDao
import com.example.cool_tour.data.local.dao.POIDao
import com.example.cool_tour.data.local.dao.RutaDao
import com.example.cool_tour.data.local.entity.AudioCacheEntity
import com.example.cool_tour.data.local.entity.POIEntity
import com.example.cool_tour.data.local.entity.RutaEntity

@Database(
    entities = [POIEntity::class, RutaEntity::class, AudioCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun poiDao(): POIDao
    abstract fun rutaDao(): RutaDao
    abstract fun audioCacheDao(): AudioCacheDao
}