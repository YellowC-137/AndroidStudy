package com.example.busschedule

import android.app.Application
import com.example.busschedule.data.MyDataBase

class BusApplication : Application() {
    val db: MyDataBase by lazy {
        MyDataBase.getDB(this)
    }
}