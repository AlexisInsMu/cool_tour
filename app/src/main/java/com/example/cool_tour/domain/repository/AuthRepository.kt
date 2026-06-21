package com.example.cool_tour.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<String>
    suspend fun registrar(email: String, password: String, nombre: String): Result<Unit>
    suspend fun isLoggedIn(): Boolean
    suspend fun logout()
}