package com.example.cool_tour.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool_tour.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: HistorialRutasAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistorialRutasAdapter()
        binding.rvHistorialRutas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistorialRutas.adapter = adapter

        viewModel.historialRutas.observe(viewLifecycleOwner) { rutas ->
            adapter.submitList(rutas)
            binding.tvRutasCompletadas.text = "${rutas.size} rutas"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}