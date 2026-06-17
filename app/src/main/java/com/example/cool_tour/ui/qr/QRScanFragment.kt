package com.example.cool_tour.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cool_tour.databinding.FragmentQrScanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QRScanFragment : Fragment() {

    private var _binding: FragmentQrScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QRScanViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQrScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSimularScan.setOnClickListener {
            viewModel.validarQR("TOUR-DEMO-001")
        }
        viewModel.resultado.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { cupon ->
                    Toast.makeText(requireContext(),
                        "¡Cupón válido! ${cupon.descuentoPorcentaje}% de descuento",
                        Toast.LENGTH_LONG).show()
                },
                onFailure = {
                    Toast.makeText(requireContext(), "QR inválido", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}