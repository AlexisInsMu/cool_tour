package com.example.cool_tour.ui.poi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cool_tour.domain.model.POI
import com.example.cool_tour.domain.repository.POIRepository
import com.example.cool_tour.domain.usecase.ActivarAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class POIDetailViewModel @Inject constructor(
    private val poiRepository: POIRepository,
    private val activarAudioUseCase: ActivarAudioUseCase
) : ViewModel() {

    private val _poi = MutableLiveData<POI?>()
    val poi: LiveData<POI?> = _poi

    fun cargarPOI(id: String) {
        viewModelScope.launch {
            _poi.value = poiRepository.getPOIById(id)
        }
    }

    fun reproducirAudio() {
        _poi.value?.let { activarAudioUseCase(it) }
    }
}