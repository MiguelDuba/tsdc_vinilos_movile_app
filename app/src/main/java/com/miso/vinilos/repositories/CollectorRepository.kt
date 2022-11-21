package com.miso.vinilos.repositories

import android.app.Application
import android.content.Context
import com.android.volley.VolleyError
import com.miso.vinilos.models.Collector
import com.miso.vinilos.network.NetworkServiceAdapter

class CollectorRepository ( val application: Application) {
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
            onError
        )
    }
}