package com.example.cool_tour.domain.model

data class Ruta(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val pois: List<POI>,
    val distanciaKm: Double
)