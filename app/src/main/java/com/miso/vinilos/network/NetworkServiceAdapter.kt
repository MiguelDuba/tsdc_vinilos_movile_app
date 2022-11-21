package com.miso.vinilos.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Artist
import com.miso.vinilos.models.Collector
import org.json.JSONArray

class NetworkServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL = "https://back-vinyls-populated.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest("albums",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i,
                            Album(
                                albumId = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                recordLabel = item.getString("recordLabel"),
                                releaseDate = item.getString("releaseDate"),
                                genre = item.getString("genre"),
                                description = item.getString("description"),
                                imageResourceId = item.getInt("id")
                            )
                        )
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }

    fun getArtists(onComplete:(resp:List<Artist>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artist(artistId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getCollectors(
        onComplete: (resp: List<Collector>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val collectorsArray = JSONArray(response)
                var collectorlist = mutableListOf<Collector>()
                for (i in 0 until collectorsArray.length()) {
                    val item = collectorsArray.getJSONObject(i)
                    collectorlist.add(
                        i,
                        Collector(
                            collectorId = item.getInt("id"),
                            name = item.getString("name"),
                            telephone = item.getString("telephone"),
                            email = item.getString("email")
                        )
                    )
                }
                onComplete(collectorlist)
            },
            Response.ErrorListener { onError(it) }
        ))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

}