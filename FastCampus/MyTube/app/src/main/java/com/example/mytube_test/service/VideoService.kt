package com.example.mytube_test.service

import com.example.mytube_test.model.Video
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {
    @GET("/v3/9e95fc5e-5b88-40f1-b587-f487aa03fa8b")
    fun getvideos(): Call<Video>

}