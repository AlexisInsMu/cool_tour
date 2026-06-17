package com.example.cool_tour.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutas")
data class RutaEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val descripcion: String,
    val poisIds: String,
    val distanciaKm: Double
)