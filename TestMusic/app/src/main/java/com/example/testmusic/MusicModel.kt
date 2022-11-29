package com.example.testmusic

class MusicModel(
    val id: Int,
    val track: String,
    val stream: String,
    val artist: String,
    val coverImg: String,
    val isPlaying: Boolean = false
)