package com.example.cool_tour.domain.repository

interface AudioRepository {
    fun reproducir(url: String)
    fun pausar()
    fun detener()
    fun isReproduciendo(): Boolean
}