package com.example.mytube_test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytube_test.databinding.ActivityMainBinding
import com.example.mytube_test.model.Video
import com.example.mytube_test.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PlayerFragment())
            .commit()

        videoAdapter = VideoAdapter(callback = { url, title ->
            supportFragmentManager.fragments.find { it is PlayerFragment }?.let {
                it as PlayerFragment
                it.playVideo(url, title)
            }
        })
        getVideo()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = videoAdapter
        setContentView(binding.root)
    }

    fun getVideo() {
        val retrofit = Retrofit.Builder().baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create()).build()

        retrofit.create(VideoService::class.java).also {
            it.getvideos().enqueue(object : Callback<Video> {
                override fun onResponse(call: Call<Video>, response: Response<Video>) {
                    if (response.isSuccessful) {
                        Log.d("MAIN", response.body()?.videos.toString())
                        videoAdapter.submitList(response.body()?.videos)
                    }
                }

                override fun onFailure(call: Call<Video>, t: Throwable) {
                    Log.d("MAIN", "onResponse: FAIL")
                }

            }
            )
        }
    }
}