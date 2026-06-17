package com.example.cool_tour.di

import android.content.Context
import com.example.cool_tour.data.repository.GeofenceRepositoryImpl
import com.example.cool_tour.domain.repository.GeofenceRepository
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeofenceModule {
    @Provides
    @Singleton
    fun provideGeofencingClient(@ApplicationContext context: Context): GeofencingClient =
        LocationServices.getGeofencingClient(context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class GeofenceBindModule {
    @Binds
    abstract fun bindGeofenceRepository(impl: GeofenceRepositoryImpl): GeofenceRepository
}