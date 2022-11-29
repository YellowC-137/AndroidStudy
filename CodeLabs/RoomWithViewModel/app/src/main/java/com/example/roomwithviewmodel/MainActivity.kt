package com.example.roomwithviewmodel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomwithviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindig: ActivityMainBinding
    private val newWordActivityRequestCode = 1
    private val viewModel: MainViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindig.root)


        val adapter = WordAdapter()
        bindig.recyclerview.adapter = adapter

        viewModel.allWord.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }


        bindig.fab.setOnClickListener {
            startActivityForResult(
                Intent(this, NewWordActivity::class.java),
                newWordActivityRequestCode
            )
        }

        bindig.fab2.setOnClickListener {
            viewModel.delete()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                Log.d("RESULT", "onActivityResult: $it")
                val word = Word(0, it.toString())
                viewModel.insert(word)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}