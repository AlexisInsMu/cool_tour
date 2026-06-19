package com.example.cool_tour.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCurrentLocation(): Flow<Location>


}


