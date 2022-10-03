package com.example.roomwithviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//inherit 오류!
class WordViewModelFactory(private val repository: WordRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}