package com.example.testmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testmusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction().replace(R.id.container,MainFragment.newInstance()).commit()
        setContentView(binding.root)
    }

} 