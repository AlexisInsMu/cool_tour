package com.example.cool_tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cool_tour.databinding.FragmentLoginBinding
import com.example.cool_tour.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(email, password)
        }

        binding.tvIrARegistro.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registro)
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            when (estado) {
                is LoginEstado.Idle -> mostrarCargando(false)
                is LoginEstado.Cargando -> mostrarCargando(true)
                is LoginEstado.Exito -> {
                    mostrarCargando(false)
                    findNavController().navigate(R.id.action_login_to_map)
                }
                is LoginEstado.Error -> {
                    mostrarCargando(false)
                    binding.tvErrorLogin.text = estado.mensaje
                    binding.tvErrorLogin.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun mostrarCargando(mostrar: Boolean) {
        binding.progressLogin.visibility = if (mostrar) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !mostrar
        if (mostrar) binding.tvErrorLogin.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}