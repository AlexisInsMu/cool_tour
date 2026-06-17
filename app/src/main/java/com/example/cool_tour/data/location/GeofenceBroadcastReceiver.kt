package com.example.cool_tour.data.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cool_tour.data.service.AudioPlayerService
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val event = GeofencingEvent.fromIntent(intent) ?: return

        if (event.hasError()) {
            Log.e("Geofence", "Error: ${GeofenceStatusCodes.getStatusCodeString(event.errorCode)}")
            return
        }

        event.triggeringGeofences?.forEach { geofence ->
            Log.d("Geofence", "Entrando a POI: ${geofence.requestId}")
            val audioIntent = Intent(context, AudioPlayerService::class.java).apply {
                putExtra("POI_ID", geofence.requestId)
            }
            context.startService(audioIntent)
        }
    }
}