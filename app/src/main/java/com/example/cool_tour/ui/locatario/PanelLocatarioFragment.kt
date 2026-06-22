package com.example.cool_tour.ui.locatario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cool_tour.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool_tour.databinding.FragmentPanelLocatarioBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PanelLocatarioFragment : Fragment() {

    private var _binding: FragmentPanelLocatarioBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PanelLocatarioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPanelLocatarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Reutiliza tu POIAdapter existente
        val adapter = LocatarioPOIAdapter { poi ->
            val bundle = Bundle().apply { putString("poiId", poi.id) }
            findNavController().navigate(R.id.action_panelLocatario_to_poi, bundle)
        }
        binding.rvMisPOIs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMisPOIs.adapter = adapter

        lifecycleScope.launch {
            viewModel.pois.collect { adapter.submitList(it) }
        }
        // cerrarSesion() es suspend — el ViewModel ya lo maneja en viewModelScope
        binding.btnCerrarSesionLocatario.setOnClickListener {
            viewModel.cerrarSesion()
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            viewModel.pois.collect { adapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}