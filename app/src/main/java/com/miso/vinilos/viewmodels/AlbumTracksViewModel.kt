package com.miso.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.miso.vinilos.models.Track
import com.miso.vinilos.network.NetworkServiceAdapter

class AlbumTracksViewModel(application: Application, albumId: Int):  AndroidViewModel(application) {

    val id:Int = albumId
    private val tracksList = MutableLiveData<List<Track>>()

    val tracks: LiveData<List<Track>>
        get() = tracksList

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        NetworkServiceAdapter.getInstance(getApplication()).getTracksAlbum(id,{
            tracksList.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumTracksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumTracksViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}