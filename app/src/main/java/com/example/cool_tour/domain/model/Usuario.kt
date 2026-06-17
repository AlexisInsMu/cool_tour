package com.example.cool_tour.domain.model

data class Usuario(
    val id: String,
    val nombre: String,
    val historialRutaIds: List<String> = emptyList(),
    val logros: List<Logro> = emptyList()
)