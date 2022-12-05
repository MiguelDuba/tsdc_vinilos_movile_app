package com.miso.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.miso.vinilos.models.Album
import com.miso.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun createAlbum(album: JSONObject):Album{
        return NetworkServiceAdapter.getInstance(application).createAlbum(album)
    }
}