package com.example.cool_tour.ui.locatario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cool_tour.R
import com.example.cool_tour.databinding.FragmentLoginLocatarioBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginLocatarioFragment : Fragment() {

    private var _binding: FragmentLoginLocatarioBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginLocatarioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginLocatarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoginLocatario.setOnClickListener {
            viewModel.login(
                email = binding.etEmailLocatario.text.toString(),
                password = binding.etPasswordLocatario.text.toString()
            )
        }

        lifecycleScope.launch {
            viewModel.estado.collect { estado ->
                when (estado) {
                    is LoginLocatarioState.Cargando -> {
                        binding.btnLoginLocatario.isEnabled = false
                        binding.progressLogin.visibility = View.VISIBLE
                    }
                    is LoginLocatarioState.Exito -> {
                        findNavController().navigate(
                            R.id.action_loginLocatario_to_panelLocatario
                        )
                    }
                    is LoginLocatarioState.Error -> {
                        binding.btnLoginLocatario.isEnabled = true
                        binding.progressLogin.visibility = View.GONE
                        Toast.makeText(requireContext(), estado.mensaje, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        binding.btnLoginLocatario.isEnabled = true
                        binding.progressLogin.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}