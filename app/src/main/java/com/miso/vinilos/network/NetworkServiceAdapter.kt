package com.miso.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Artist
import com.miso.vinilos.models.Collector
import com.miso.vinilos.models.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL = "https://vynils-back-dvs.herokuapp.com/"
        // const val BASE_URL= "https://vynils-back-dvs.herokuapp.com/"
        // const val BASE_URL= "https://back-vinyls-populated.herokuapp.com/"
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

    suspend fun getAlbums()= suspendCoroutine<List<Album>>{ cont ->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                        imageResourceId = item.getInt("id")))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))

    }

    suspend fun getArtists()= suspendCoroutine<List<Artist>>{ cont ->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Artist(artistId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    fun getArtistDetail(id: Int, onComplete:(resp:Artist)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("musicians/$id",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)

                onComplete(Artist(artistId = resp.getInt("id"),name = resp.getString("name"), description = resp.getString("description"), birthDate = resp.getString("birthDate"), image = resp.getString("image")))
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getAlbum(id: Int, onComplete:(resp:Album)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("albums/$id",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                onComplete(Album(
                    albumId = resp.getInt("id"),
                    name = resp.getString("name"),
                    cover = resp.getString("cover"),
                    releaseDate = resp.getString("releaseDate"),
                    description = resp.getString("description"),
                    genre = resp.getString("releaseDate"),
                    recordLabel = resp.getString("releaseDate"),
                    imageResourceId = resp.getInt("id")
                ))
            },
            Response.ErrorListener {
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
                var item:JSONObject? = null
                for (i in 0 until collectorsArray.length()) {
                    item = collectorsArray.getJSONObject(i)
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

    fun getTracksAlbum(
        id: Int,
        onComplete: (resp: List<Track>) -> Unit,
        onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("albums/$id/tracks",
            Response.Listener<String> { response ->
                val tracksArray = JSONArray(response)
                var trackList = mutableListOf<Track>()
                var item:JSONObject? = null
                for (i in 0 until tracksArray.length()) {
                    item = tracksArray.getJSONObject(i)
                    trackList.add(
                        i,
                        Track(
                            trackId = item.getInt("id"),
                            name = item.getString("name"),
                            duration = item.getString("duration")
                        )
                    )
                }
                onComplete(trackList)
            },
            Response.ErrorListener { onError(it) }
        ))
    }

    fun getCollectorDetail(id: Int, onComplete:(resp:Collector)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("collectors/$id",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)

                onComplete(Collector(
                    collectorId = resp.getInt("id"),
                    name = resp.getString("name"),
                    telephone = resp.getString("telephone"),
                    email = resp.getString("email")))
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    suspend fun createAlbum(body: JSONObject) = suspendCoroutine<Album>{ cont->
        requestQueue.add(postRequest("albums", body,
            { response ->
                val album=Album(albumId = response.getInt("id"),name = response.getString("name"), cover = response.getString("cover"), recordLabel = response.getString("recordLabel"), releaseDate = response.getString("releaseDate"), genre = response.getString("genre"), description = response.getString("description"), imageResourceId = response.getInt("id"))
                cont.resume(album)
            },
            {
                Log.d("Crear album", "ERROR")
                cont.resumeWithException(it)
            }))
    }

    suspend fun getTracksByAlbumId(albumId:Int) = suspendCoroutine<List<Track>>{ cont ->
        requestQueue.add(getRequest("albums/"+albumId.toString()+"/tracks",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Track>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Track(trackId = item.getInt("id"),
                        name = item.getString("name"),
                        duration = item.getString("duration"))
                    )
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun createTrack(body: JSONObject, albumId: Int) = suspendCoroutine<Track>{ cont->
        requestQueue.add(postRequest("albums/"+albumId.toString()+"/tracks", body,
            { response ->
                val track=Track(trackId = response.getInt("id"),name = response.getString("name"), duration = response.getString("duration"))
                cont.resume(track)
            },
            {
                Log.d("Crear Cancion", "ERROR")
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
}