package com.example.cool_tour.ui.missions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cool_tour.domain.model.Mision
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MissionsViewModel @Inject constructor() : ViewModel() {

    private val _misiones = MutableLiveData<List<Mision>>()
    val misiones: LiveData<List<Mision>> = _misiones

    init {
        _misiones.value = listOf(
            Mision("M1", "Explorador", "Visita 3 POIs", listOf("P1", "P2", "P3"), false),
            Mision("M2", "Fotógrafo", "Visita el mirador", listOf("P4"), false)
        )
    }
}