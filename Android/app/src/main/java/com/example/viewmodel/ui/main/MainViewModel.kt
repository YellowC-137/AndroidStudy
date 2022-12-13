package com.example.viewmodel.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val rate = 0.8f
    var dollar : MutableLiveData<String> = MutableLiveData()
    var result : MutableLiveData<Float> = MutableLiveData()

    fun set(value: String){
        this.dollar.value = value
        result.value = value.toFloat()* rate
    }
    fun result():MutableLiveData<Float>{
        val test = "TEST"
        savedStateHandle[test] = "SAVED"
        return result
    }

    fun reload(){
        var saveLive : LiveData<String> = savedStateHandle.getLiveData("TEST")
        var save = savedStateHandle.get<String>("TEST")
    }


}