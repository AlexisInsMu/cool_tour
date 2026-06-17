package com.example.cool_tour.domain.usecase

import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.repository.AudioRepository
import javax.inject.Inject

class ActivarAudioUseCase @Inject constructor(
    private val audioRepository: AudioRepository
) {
    operator fun invoke(poi: POI) {
        if (poi.audioUrl.isNotBlank()) {
            audioRepository.reproducir(poi.audioUrl)
        }
    }
}
