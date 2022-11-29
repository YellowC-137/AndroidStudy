package com.example.testmusic

import com.example.testmusic.service.MusicEntity

fun MusicEntity.mapper(id:Int):MusicModel =
    MusicModel(
        id = id,
        track = track,
        stream = stream,
        artist = artist,
        coverImg = coverImg
    )