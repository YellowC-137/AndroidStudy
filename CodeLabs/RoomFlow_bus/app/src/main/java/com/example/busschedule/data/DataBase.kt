package com.example.busschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Schedule::class), version = 1)
abstract class MyDataBase : RoomDatabase() {
    companion object {
        @Volatile
        private var Instance: MyDataBase? = null

        fun getDB(context: Context): MyDataBase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java, "db"
                ).createFromAsset("data/schedule.db").build()

                Instance = instance

                instance
            }
        }
    }

    abstract fun scheduleDao(): ScheduleDao

}