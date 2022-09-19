package com.example.viewmodel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel : ViewModel() {

    private val UsdToWon = 1400
    private var txt = ""
    private var result: Int = 0

    fun Cal(value:Int) {
        this.txt = value.toString()
        result = value*UsdToWon

    }
    fun getResult():Int?{
        return result
    }
}