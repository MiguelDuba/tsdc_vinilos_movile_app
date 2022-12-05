package com.miso.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.miso.vinilos.models.Track
import com.miso.vinilos.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class TrackCreateViewModel(application: Application) :  AndroidViewModel(application) {
    private val trackRepository = TrackRepository(application)

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track>
        get() = _track

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {

    }

    suspend fun createTrackFromNetwork(track: JSONObject, albumId: Int):Int {
        var id:Int=0
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = trackRepository.createTrack(track, albumId)
                    _track.postValue(data)
                    id=data.trackId
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
        return id
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackCreateViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}