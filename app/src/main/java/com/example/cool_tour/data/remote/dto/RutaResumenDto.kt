package com.example.cool_tour.data.remote.dto
import com.example.cool_tour.domain.model.RutaResumen

data class RutaResumenDto(
    val id: String,
    val nombre: String,
    val resumenCorto: String,
    val tiempoEstimadoMin: Int,
    val dificultad: String,
    val cantidadPois: Int,
    val imagenPortada: String?
)

fun RutaResumenDto.toDomain() = RutaResumen(
    id = id,
    nombre = nombre,
    resumenCorto = resumenCorto,
    tiempoEstimadoMin = tiempoEstimadoMin,
    dificultad = dificultad,
    cantidadPois = cantidadPois,
    imagenPortada = imagenPortada
)