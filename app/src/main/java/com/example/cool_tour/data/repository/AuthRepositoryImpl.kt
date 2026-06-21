package com.example.cool_tour.data.repository

import com.example.cool_tour.data.local.SessionManager
import com.example.cool_tour.data.remote.ApiService
import com.example.cool_tour.data.remote.dto.LoginRequest
import com.example.cool_tour.data.remote.dto.RegistroRequest
import com.example.cool_tour.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            sessionManager.guardarSesion(response.token, response.usuario.nombre)
            Result.success(response.token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registrar(email: String, password: String, nombre: String): Result<Unit> {
        return try {
            apiService.registrar(RegistroRequest(email, password, nombre))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isLoggedIn(): Boolean = sessionManager.obtenerToken() != null

    override suspend fun logout() = sessionManager.cerrarSesion()
}