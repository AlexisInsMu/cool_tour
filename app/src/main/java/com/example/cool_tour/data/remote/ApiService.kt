package com.example.cool_tour.data.remote

import com.example.cool_tour.data.local.dao.RutaDetalleDto
import com.example.cool_tour.data.remote.dto.LoginLocatarioResponse
import com.example.cool_tour.data.remote.dto.LoginRequest
import com.example.cool_tour.data.remote.dto.LoginResponse
import com.example.cool_tour.data.remote.dto.POIDto
import com.example.cool_tour.data.remote.dto.RegistroRequest
import com.example.cool_tour.data.remote.dto.RegistroResponse
import com.example.cool_tour.data.remote.dto.RutaRequest
import com.example.cool_tour.data.remote.dto.RutaResponse
import com.example.cool_tour.data.remote.dto.RutaResumenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("api/rutas/explorar")
    suspend fun getRutasExplorar(): List<RutaResumenDto>

    @GET("api/rutas/{id}")
    suspend fun getRutaDetalle(@Path("id") id: String): RutaDetalleDto

    @POST("api/locatarios/login")
    suspend fun loginLocatario(@Body body: Map<String, String>): LoginLocatarioResponse

    @GET("api/locatarios/mis-pois")
    suspend fun getMisPOIs(@Header("Authorization") token: String): List<POIDto>

    @GET("api/rutas/{id}")
    suspend fun getRutaDetalle(
        @Header("Authorization") token: String,
        @Path("id") rutaId: String
    ): RutaResponse
}