package com.example.cool_tour.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cool_tour.databinding.FragmentQrScanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QRScanFragment : Fragment() {

    private var _binding: FragmentQrScanBinding? = null
    private val binding get() = _binding!!

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            mostrarCamaraActiva()
        } else {
            mostrarPermisoDenegado()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSolicitarPermiso.setOnClickListener {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        verificarPermisoCamara()
    }

    private fun verificarPermisoCamara() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                mostrarCamaraActiva()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // El usuario negó antes — explicar por qué lo necesitamos
                binding.tvEstadoCamara.text = "La cámara es necesaria para escanear códigos QR"
                binding.tvEstadoCamara.setTextColor(android.graphics.Color.parseColor("#FF9800"))
                binding.btnSolicitarPermiso.visibility = View.VISIBLE
            }

            else -> {
                // Primera vez — pedir permiso directamente
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun mostrarCamaraActiva() {
        binding.tvEstadoCamara.text = "✅ Cámara lista — función QR en desarrollo"
        binding.tvEstadoCamara.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
        binding.btnSolicitarPermiso.visibility = View.GONE
        // Aquí se integrará CameraX en una fase posterior
    }

    private fun mostrarPermisoDenegado() {
        binding.tvEstadoCamara.text = "⚠️ Sin permiso de cámara"
        binding.tvEstadoCamara.setTextColor(android.graphics.Color.parseColor("#F44336"))
        binding.btnSolicitarPermiso.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}