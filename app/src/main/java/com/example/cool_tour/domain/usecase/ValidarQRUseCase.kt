package com.example.cool_tour.domain.usecase

import com.example.cool_tour.domain.model.Cupon
import javax.inject.Inject

class ValidarQRUseCase @Inject constructor() {

    operator fun invoke(codigoQR: String): Result<Cupon> {
        return if (codigoQR.startsWith("TOUR-")) {
            Result.success(
                Cupon(
                    id = "CUP-001",
                    codigo = codigoQR,
                    descuentoPorcentaje = 15,
                    locatarioId = "LOC-001",
                    expirado = false
                )
            )
        } else {
            Result.failure(IllegalArgumentException("Código QR inválido"))
        }
    }
}