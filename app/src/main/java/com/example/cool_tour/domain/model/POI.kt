package com.example.cool_tour.domain.model

data class POI(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double,
    val audioUrl: String,
    val imageUrl: String,
    val radioMetros: Float = 50f
)