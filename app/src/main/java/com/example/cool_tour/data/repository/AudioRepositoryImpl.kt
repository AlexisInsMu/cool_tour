package com.example.cool_tour.data.repository

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.cool_tour.data.service.AudioPlayerService
import com.example.cool_tour.domain.repository.AudioRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioRepository {

    private var service: AudioPlayerService? = null

    private val conexion = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = (binder as AudioPlayerService.AudioBinder).getService()
        }
        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }
    }

    init {
        val intent = Intent(context, AudioPlayerService::class.java)
        context.startService(intent)
        context.bindService(intent, conexion, Context.BIND_AUTO_CREATE)
    }

    override fun reproducir(url: String) { service?.reproducir(url) }
    override fun pausar() { service?.pausar() }
    override fun detener() { service?.detener() }
    override fun isReproduciendo() = service?.isReproduciendo() == true
}