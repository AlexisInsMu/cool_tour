package com.example.cool_tour.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool_tour.R
import com.example.cool_tour.data.local.SessionManager
import com.example.cool_tour.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historialAdapter = HistorialRutasAdapter()
        binding.rvHistorialRutas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistorialRutas.adapter = historialAdapter

        val pendientesAdapter = HistorialRutasAdapter()
        binding.rvRutasPorVisitar.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRutasPorVisitar.adapter = pendientesAdapter

        viewModel.historialRutas.observe(viewLifecycleOwner) { rutas ->
            historialAdapter.submitList(rutas)
            binding.tvContadorRutasCompletadas.text = rutas.size.toString()
        }

        viewModel.rutasPorVisitar.observe(viewLifecycleOwner) { rutas ->
            pendientesAdapter.submitList(rutas)
            binding.tvContadorRutasPendientes.text = rutas.size.toString()
        }

        // Botón locatario
        binding.btnAccesoLocatario.setOnClickListener {
            lifecycleScope.launch {
                if (sessionManager.estaLogueadoComoLocatario()) {
                    findNavController().navigate(R.id.action_perfil_to_panelLocatario)
                } else {
                    findNavController().navigate(R.id.action_perfil_to_loginLocatario)
                }
            }
        }

        // Cerrar sesión
        binding.btnCerrarSesion.setOnClickListener {
            lifecycleScope.launch {
                sessionManager.cerrarSesion()
            }
            findNavController().navigate(R.id.action_perfil_to_login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}