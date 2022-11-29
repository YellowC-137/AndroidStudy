package com.example.mytube_test

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytube_test.databinding.FragmentPlayerBinding
import com.example.mytube_test.model.Video
import com.example.mytube_test.service.VideoService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.abs


class PlayerFragment : Fragment() {
    private var binding: FragmentPlayerBinding? = null
    private lateinit var videoAdapter: VideoAdapter
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playerBinding = FragmentPlayerBinding.bind(view)
        binding = playerBinding


        inintMotionLayout(playerBinding)
        initRecyclerView(playerBinding)
        initPlayer(playerBinding)
        initbtn(playerBinding)


    }

    private fun initbtn(playerBinding: FragmentPlayerBinding) {
        playerBinding.imgBtn.setOnClickListener {
            val player = this.exoPlayer ?: return@setOnClickListener
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }
    }

    private fun initPlayer(playerBinding: FragmentPlayerBinding) {
        context?.let {
            exoPlayer = ExoPlayer.Builder(it).build()
        }
        playerBinding.playerView.player = exoPlayer
        playerBinding.let {
            exoPlayer?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    if (isPlaying) {
                        it.imgBtn.setImageResource(R.drawable.ic_baseline_stop_circle_24)
                    } else {
                        it.imgBtn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                    }

                }
            })
        }


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

    private fun initRecyclerView(playerBinding: FragmentPlayerBinding) {
        getVideo()
        videoAdapter = VideoAdapter(callback = { url, title ->
            playVideo(url, title)
        })
        playerBinding.playerRcv.layoutManager = LinearLayoutManager(requireActivity())
        playerBinding.playerRcv.adapter = videoAdapter
    }

    private fun inintMotionLayout(playerBinding: FragmentPlayerBinding) {
        playerBinding.playerMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                binding.let {
                    (requireActivity() as MainActivity).also { it ->
                        it.findViewById<MotionLayout>(R.id.main_motion_layout).progress =
                            abs(progress)
                    }
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}
            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })
    }

    fun playVideo(url: String, title: String) {
        context?.let {
            val dataSourceFactory = DefaultDataSource.Factory(it)
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(Uri.parse(url))
            )
            exoPlayer?.setMediaSource(mediaSource)
            exoPlayer?.prepare()
            exoPlayer?.play()
        }

        binding?.let {
            it.playerMotionLayout.transitionToEnd()
            it.title.text = title
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.pause()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
        exoPlayer?.release()
    }

}