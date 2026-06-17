package com.example.cool_tour.domain.usecase

import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.model.Ruta
import java.util.UUID
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class CalcularRutaUseCase @Inject constructor() {

    operator fun invoke(pois: List<POI>): Ruta {
        return Ruta(
            id = UUID.randomUUID().toString(),
            nombre = "Ruta personalizada",
            descripcion = "Ruta con ${pois.size} puntos de interés",
            pois = pois,
            distanciaKm = calcularDistanciaTotal(pois)
        )
    }

    private fun calcularDistanciaTotal(pois: List<POI>): Double {
        if (pois.size < 2) return 0.0
        var total = 0.0
        for (i in 0 until pois.size - 1) {
            total += haversine(pois[i].latitud, pois[i].longitud,
                pois[i + 1].latitud, pois[i + 1].longitud)
        }
        return total
    }

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        return r * 2 * asin(sqrt(a))
    }
}