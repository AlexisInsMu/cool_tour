package com.example.cool_tour.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cool_tour.databinding.FragmentMissionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MissionsFragment : Fragment() {

    private var _binding: FragmentMissionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MissionsViewModel by viewModels()
    private lateinit var adapter: MissionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MissionsAdapter()
        binding.rvMisiones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMisiones.adapter = adapter
        viewModel.misiones.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}