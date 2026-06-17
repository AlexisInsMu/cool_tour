package com.example.cool_tour.di

import com.example.cool_tour.data.repository.RutaRepositoryImpl
import com.example.cool_tour.domain.repository.RutaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RutaModule {
    @Binds
    abstract fun bindRutaRepository(impl: RutaRepositoryImpl): RutaRepository
}