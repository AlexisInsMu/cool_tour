package com.example.cool_tour.data.remote.dto

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String, val usuario: UsuarioDto)
data class UsuarioDto(val id: String, val nombre: String)

data class RegistroRequest(val email: String, val password: String, val nombre: String)
data class RegistroResponse(val id: String, val email: String, val nombre: String)

data class POIDto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val latitud: Double,
    val longitud: Double,
    val radioMetros: Float,
    val audioUrl: String,
    val imageUrl: String?,
    val categoria: String?
)

data class RutaRequest(val nombre: String, val poisIds: List<String>)
data class RutaResponse(
    val id: String,
    val nombre: String,
    val estado: String,
    val descripcion: String?,
    val pois: List<RutaPoiDto>
)
data class RutaPoiDto(val orden: Int, val poi: POIDto)