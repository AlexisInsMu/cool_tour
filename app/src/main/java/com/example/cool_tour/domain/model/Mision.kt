package com.example.cool_tour.domain.model

data class Mision(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val poisRequeridos: List<String>,
    val completada: Boolean = false
)