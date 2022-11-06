package com.miso.vinilos.models

import androidx.annotation.DrawableRes

data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    @DrawableRes val imageResourceId: Int
)
