package com.example.cool_tour.domain.model

data class RutaResumen(
    val id: String,
    val nombre: String,
    val resumenCorto: String,
    val tiempoEstimadoMin: Int,
    val dificultad: String,
    val cantidadPois: Int,
    val imagenPortada: String?
)
