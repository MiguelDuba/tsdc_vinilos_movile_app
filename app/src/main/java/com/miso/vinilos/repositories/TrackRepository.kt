package com.miso.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.miso.vinilos.models.Album
//import com.miso.vinilos.database.TracksDao
import com.miso.vinilos.models.Track
import com.miso.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject


class TrackRepository (val application: Application){

    suspend fun refreshData(albumId: Int): List<Track> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getTracksByAlbumId(albumId)
    }

    suspend fun createTrack(track: JSONObject, albumId: Int):Track{
        return NetworkServiceAdapter.getInstance(application).createTrack(track, albumId)
    }
}