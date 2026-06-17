package com.example.cool_tour.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.cool_tour.R
import com.example.cool_tour.ui.MainActivity

class AudioPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private val binder = AudioBinder()

    inner class AudioBinder : Binder() {
        fun getService() = this@AudioPlayerService
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        crearCanalNotificacion()
        startForeground(NOTIFICATION_ID, crearNotificacion("Listo para reproducir"))
    }

    fun reproducir(url: String) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
        }
        actualizarNotificacion("Reproduciendo audio")
    }

    fun pausar() {
        mediaPlayer?.pause()
        actualizarNotificacion("Audio en pausa")
    }

    fun detener() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        actualizarNotificacion("Audio detenido")
    }

    fun isReproduciendo() = mediaPlayer?.isPlaying == true

    private fun crearCanalNotificacion() {
        val canal = NotificationChannel(CHANNEL_ID, "Audio Cool Tour", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(canal)
    }

    private fun crearNotificacion(texto: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🎵 Cool Tour Audio")
            .setContentText(texto)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun actualizarNotificacion(texto: String) {
        getSystemService(NotificationManager::class.java).notify(NOTIFICATION_ID, crearNotificacion(texto))
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

    companion object {
        const val CHANNEL_ID = "cool_tour_audio_channel"
        const val NOTIFICATION_ID = 1001
    }
}