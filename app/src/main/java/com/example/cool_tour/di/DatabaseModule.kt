package com.example.cool_tour.di

import android.content.Context
import androidx.room.Room
import com.example.cool_tour.data.local.AppDatabase
import com.example.cool_tour.data.local.dao.AudioCacheDao
import com.example.cool_tour.data.local.dao.POIDao
import com.example.cool_tour.data.local.dao.RutaDao
import com.example.cool_tour.data.repository.POIRepositoryImpl
import com.example.cool_tour.domain.repository.POIRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "cool_tour_db").build()

    @Provides fun providePoiDao(db: AppDatabase): POIDao = db.poiDao()
    @Provides fun provideRutaDao(db: AppDatabase): RutaDao = db.rutaDao()
    @Provides fun provideAudioCacheDao(db: AppDatabase): AudioCacheDao = db.audioCacheDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun bindPOIRepository(impl: POIRepositoryImpl): POIRepository
}