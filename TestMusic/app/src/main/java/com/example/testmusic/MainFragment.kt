package com.example.testmusic

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.testmusic.databinding.FragmentMainBinding
import com.example.testmusic.service.MusicDto
import com.example.testmusic.service.MusicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private var isWatching = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        initPlsyList(binding)
        getMusic()

    }

    private fun initPlsyList(binding: FragmentMainBinding) {
        binding.playlistImageView.setOnClickListener {
            binding.playerViewGroup.isVisible = isWatching
            binding.playListViewGroup.isVisible = isWatching.not()

            isWatching = !isWatching
        }
    }

    private fun getMusic() {
        val retrofit = Retrofit.Builder().baseUrl("https:run.mocky.io").addConverterFactory(GsonConverterFactory.create()).build()

        retrofit.create(MusicService::class.java).also {
            it.listMusics().enqueue(
                object : Callback<MusicDto>{
                    override fun onResponse(call: Call<MusicDto>, response: Response<MusicDto>) {
                        Log.d("MUSIC", response.body()!!.musics[0].artist)
                        response.body()?.let {
                            it.musics.mapIndexed{
                                index, musicEntity ->
                                musicEntity.mapper(index)
                            }
                        }
                    }

                    override fun onFailure(call: Call<MusicDto>, t: Throwable) {
                        Log.d("MUSIC", "onFailure: ")
                    }

                }
            )
        }
    }

    companion object{
        fun newInstance():MainFragment{
            return MainFragment()
        }
    }
}