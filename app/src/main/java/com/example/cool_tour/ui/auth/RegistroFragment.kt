package com.example.cool_tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cool_tour.R
import com.example.cool_tour.databinding.FragmentRegistroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistroViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVolverLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val email = binding.etEmailRegistro.text.toString().trim()
            val password = binding.etPasswordRegistro.text.toString().trim()
            viewModel.registrar(nombre, email, password)
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            when (estado) {
                is LoginEstado.Idle -> mostrarCargando(false)
                is LoginEstado.Cargando -> mostrarCargando(true)
                is LoginEstado.Exito -> {
                    mostrarCargando(false)
                    findNavController().navigate(R.id.action_registro_to_map)
                }
                is LoginEstado.Error -> {
                    mostrarCargando(false)
                    binding.tvErrorRegistro.text = estado.mensaje
                    binding.tvErrorRegistro.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun mostrarCargando(mostrar: Boolean) {
        binding.progressRegistro.visibility = if (mostrar) View.VISIBLE else View.GONE
        binding.btnRegistrar.isEnabled = !mostrar
        if (mostrar) binding.tvErrorRegistro.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}