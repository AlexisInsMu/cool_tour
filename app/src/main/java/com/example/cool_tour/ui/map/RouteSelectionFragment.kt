package com.example.cool_tour.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool_tour.databinding.FragmentRouteSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteSelectionFragment : Fragment() {

    private var _binding: FragmentRouteSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RouteSelectionViewModel by viewModels()
    private lateinit var adapter: POISelectionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRouteSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = POISelectionAdapter { poi, seleccionado -> viewModel.togglePOI(poi, seleccionado) }
        binding.rvPoisSeleccionables.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPoisSeleccionables.adapter = adapter
        viewModel.pois.observe(viewLifecycleOwner) { adapter.submitList(it) }
        binding.btnIniciarRuta.setOnClickListener {
            val ruta = viewModel.generarRutaLibre()
            ruta?.let {
                // Compartir ruta con MapViewModel via SharedViewModel o NavBackStackEntry
                findNavController()
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("rutaSeleccionada", it.pois.map { poi -> poi.id })
            }
            findNavController().popBackStack()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}