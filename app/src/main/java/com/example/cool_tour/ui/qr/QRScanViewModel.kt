package com.example.cool_tour.ui.qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cool_tour.domain.model.Cupon
import com.example.cool_tour.domain.usecase.ValidarQRUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QRScanViewModel @Inject constructor(
    private val validarQRUseCase: ValidarQRUseCase
) : ViewModel() {

    private val _resultado = MutableLiveData<Result<Cupon>>()
    val resultado: LiveData<Result<Cupon>> = _resultado

    fun validarQR(codigo: String) {
        _resultado.value = validarQRUseCase(codigo)
    }
}