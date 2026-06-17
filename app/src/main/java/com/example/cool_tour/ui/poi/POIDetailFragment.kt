package com.example.cool_tour.ui.poi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cool_tour.databinding.FragmentPoiDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class POIDetailFragment : Fragment() {

    private var _binding: FragmentPoiDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: POIDetailViewModel by viewModels()
    private val args: POIDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPoiDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cargarPOI(args.poiId)
        viewModel.poi.observe(viewLifecycleOwner) { poi ->
            poi?.let {
                binding.tvNombre.text = it.nombre
                binding.tvDescripcion.text = it.descripcion
            }
        }
        binding.btnReproducirAudio.setOnClickListener {
            viewModel.reproducirAudio()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}