package com.example.testmusic.service

import com.google.gson.annotations.SerializedName

data class MusicEntity(
    @SerializedName("track") val track:String,
    @SerializedName("stream") val stream:String,
    @SerializedName("artist") val artist:String,
    @SerializedName("coverImg") val coverImg:String
)
