package com.miso.vinilos.repositories

import android.app.Application
import com.miso.vinilos.models.Track
import com.miso.vinilos.network.NetworkServiceAdapter

class TrackRepository(val application: Application) {
    suspend fun refreshData(): List<Track> {
        return NetworkServiceAdapter.getInstance(application).getTracks()
    }
}