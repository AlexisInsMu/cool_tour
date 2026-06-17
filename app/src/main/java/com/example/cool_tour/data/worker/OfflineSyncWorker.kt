package com.example.cool_tour.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cool_tour.domain.repository.POIRepository
import com.example.cool_tour.domain.repository.RutaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class OfflineSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val rutaRepository: RutaRepository,
    private val poiRepository: POIRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val rutas = rutaRepository.getRutas().first()
            val pois = poiRepository.getPOIs().first()
            android.util.Log.d("OfflineSyncWorker", "Sync: ${rutas.size} rutas, ${pois.size} POIs")
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "offline_sync_worker"
    }
}