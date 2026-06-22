package com.example.cool_tour.data.remote.dto

data class LoginLocatarioResponse(
    val token: String,
    val locatario: LocatarioInfoDto
)

data class LocatarioInfoDto(
    val id: String,
    val nombre: String,
    val negocio: String,
    val plan: String
)