package com.miso.vinilos.network

import android.content.Context
import android.util.SparseArray
import androidx.collection.ArrayMap
import androidx.collection.LruCache
import androidx.collection.arrayMapOf

class CacheManager(context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

}