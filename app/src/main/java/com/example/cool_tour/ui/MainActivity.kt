package com.example.cool_tour.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cool_tour.R
import com.example.cool_tour.data.worker.OfflineSyncWorker
import com.example.cool_tour.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.visibility =
                if (destination.id == R.id.loginFragment || destination.id == R.id.registroFragment) {
                    android.view.View.GONE
                } else {
                    android.view.View.VISIBLE
                }
        }

        programarSyncWorker()

        // ← AGREGAR DESDE AQUÍ
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION), 100
                )
            }
        }
        // ← HASTA AQUÍ
    }

    private fun programarSyncWorker() {
        val workRequest = PeriodicWorkRequestBuilder<OfflineSyncWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                OfflineSyncWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

}