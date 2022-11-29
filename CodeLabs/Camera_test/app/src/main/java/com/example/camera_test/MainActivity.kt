package com.example.camera_test

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.camera_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this,OldCameraActivity::class.java))
        }
        binding.btnX.setOnClickListener {
            startActivity(Intent(this,CameraXActivity::class.java))
        }

        if (intent.data != null){
            Log.d("DATA", intent.data.toString())
            val videoUri: Uri = intent.data as Uri
            binding.album.setVideoURI(videoUri)
        }

    }
}