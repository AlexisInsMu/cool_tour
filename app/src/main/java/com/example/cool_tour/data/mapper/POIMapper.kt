package com.example.cool_tour.data.mapper

import com.example.cool_tour.data.local.entity.POIEntity
import com.example.cool_tour.domain.model.POI

fun POIEntity.toDomain() = POI(
    id = id, nombre = nombre, descripcion = descripcion,
    latitud = latitud, longitud = longitud,
    audioUrl = audioUrl, imageUrl = imageUrl, radioMetros = radioMetros
)

fun POI.toEntity() = POIEntity(
    id = id, nombre = nombre, descripcion = descripcion,
    latitud = latitud, longitud = longitud,
    audioUrl = audioUrl, imageUrl = imageUrl, radioMetros = radioMetros
)