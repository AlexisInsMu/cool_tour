package com.example.cool_tour.domain.model

data class Cupon(
    val id: String,
    val codigo: String,
    val descuentoPorcentaje: Int,
    val locatarioId: String,
    val expirado: Boolean = false
)