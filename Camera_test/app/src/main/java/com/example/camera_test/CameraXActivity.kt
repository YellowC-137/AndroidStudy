package com.example.camera_test

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.camera_test.databinding.ActivityCameraXactivityBinding

class CameraXActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraXactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraXactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}