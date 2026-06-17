package com.example.cool_tour.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pois")
data class POIEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double,
    val audioUrl: String,
    val imageUrl: String,
    val radioMetros: Float = 50f
)