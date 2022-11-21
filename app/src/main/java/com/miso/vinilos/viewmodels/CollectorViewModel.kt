package com.miso.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.miso.vinilos.models.Artist
import com.miso.vinilos.models.Collector
import com.miso.vinilos.network.NetworkServiceAdapter
import com.miso.vinilos.repositories.CollectorRepository

class CollectorViewModel(application: Application): AndroidViewModel(application) {

    private val collectorsRepository = CollectorRepository(application)

    init {
        refreshDataFromNetwork()
    }

    private val collectorList = MutableLiveData<List<Collector>>()
    val collectors : LiveData<List<Collector>>
        get() = collectorList

    private var networkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = networkError

    private var networkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = networkErrorShown

    private fun refreshDataFromNetwork() {
        collectorsRepository.refreshData({
            collectorList.postValue(it)
            networkError.value = false
            networkErrorShown.value = false
        },{
            networkErrorShown.value = true
        })
    }

    fun onNetworkErrorShown() {
        networkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}