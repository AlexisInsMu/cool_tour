package com.example.cool_tour.domain.usecase

import com.example.cool_tour.domain.model.Ruta
import com.example.cool_tour.domain.repository.RutaRepository
import javax.inject.Inject

class DescargarRutaUseCase @Inject constructor(
    private val rutaRepository: RutaRepository
) {
    suspend operator fun invoke(ruta: Ruta) {
        rutaRepository.guardarRuta(ruta)
    }
}