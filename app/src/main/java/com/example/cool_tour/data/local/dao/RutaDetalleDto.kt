package com.example.cool_tour.data.local.dao

import com.example.cool_tour.data.remote.dto.POIDto

data class RutaDetalleDto(
    val id: String,
    val nombre: String,
    val descripcion: String?,
    val pois: List<RutaPoiDto>
)

data class RutaPoiDto(
    val orden: Int,
    val poi: POIDto   // reutiliza tu POIDto existente
)