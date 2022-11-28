package com.miso.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.miso.vinilos.models.Track
import com.miso.vinilos.repositories.TrackRepository

class TrackViewModel(application: Application, albumId: Int): AndroidViewModel(application) {

    private val TracksRepository = TrackRepository(application)

    private val _tracks = MutableLiveData<List<Track>>()

    val tracks: LiveData<list<Track>>
        get() = _tracks

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = albumId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        tracksRepository.refreshData(id, {
            _tracks.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            Log.d("Error", it.toString())
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}