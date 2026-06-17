package com.example.cool_tour.data.mapper

import com.example.cool_tour.data.local.entity.RutaEntity
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.model.Ruta

fun RutaEntity.toDomain(pois: List<POI> = emptyList()) = Ruta(
    id = id, nombre = nombre, descripcion = descripcion,
    pois = pois, distanciaKm = distanciaKm
)

fun Ruta.toEntity() = RutaEntity(
    id = id, nombre = nombre, descripcion = descripcion,
    poisIds = pois.joinToString(",") { it.id },
    distanciaKm = distanciaKm
)