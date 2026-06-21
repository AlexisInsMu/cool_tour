package com.example.cool_tour.data.remote

import com.example.cool_tour.data.remote.dto.LoginRequest
import com.example.cool_tour.data.remote.dto.LoginResponse
import com.example.cool_tour.data.remote.dto.POIDto
import com.example.cool_tour.data.remote.dto.RegistroRequest
import com.example.cool_tour.data.remote.dto.RegistroResponse
import com.example.cool_tour.data.remote.dto.RutaRequest
import com.example.cool_tour.data.remote.dto.RutaResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("api/auth/registrar")
    suspend fun registrar(@Body body: RegistroRequest): RegistroResponse

    @GET("api/pois")
    suspend fun getPOIs(): List<POIDto>

    @POST("api/rutas")
    suspend fun crearRuta(
        @Header("Authorization") token: String,
        @Body body: RutaRequest
    ): RutaResponse

    @GET("api/rutas/mis-rutas")
    suspend fun misRutas(@Header("Authorization") token: String): List<RutaResponse>
}