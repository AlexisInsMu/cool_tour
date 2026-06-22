package com.example.cool_tour.ui.rutas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool_tour.databinding.FragmentExplorarRutasBinding
import com.example.cool_tour.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExplorarRutasFragment : Fragment() {

    private var _binding: FragmentExplorarRutasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExplorarRutasViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExplorarRutasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RutasDisponiblesAdapter { ruta ->
            val bundle = Bundle().apply { putString("rutaId", ruta.id) }
            findNavController().navigate(R.id.action_explorar_to_map, bundle)
        }

        binding.rvRutasDisponibles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRutasDisponibles.adapter = adapter

        viewModel.rutas.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}