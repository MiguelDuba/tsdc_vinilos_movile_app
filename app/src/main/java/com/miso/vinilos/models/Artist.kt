package com.miso.vinilos.models

import androidx.annotation.DrawableRes

data class Artist (
    val artistId:Int,
    val name:String,
    val cover:String,
    @DrawableRes val image: Int,
    val description:String,
    val birthDate:String
)