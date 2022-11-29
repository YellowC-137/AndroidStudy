package com.example.camera_test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.camera_test.databinding.ActivityOldCameraBinding

class OldCameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOldCameraBinding
    val REQUEST_VIDEO_CAPTURE = 1
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                Log.d("DATA1", intent?.data.toString())
                if (intent != null){
                    //binding.videoView.setVideoURI(Uri.parse(intent.data.toString()))
                    binding.videoView.setVideoPath(intent.data.toString())
                    binding.videoView.start()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOldCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCamera.setOnClickListener {
            dispatchTakeVideoIntent()
        }

    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startForResult.launch(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
                setResult(REQUEST_VIDEO_CAPTURE)
                Log.d("DATA2", intent.data.toString())
            }
        }
    }

}