package com.example.cool_tour.domain.model

data class Logro(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val icono: String,
    val desbloqueado: Boolean = false
)