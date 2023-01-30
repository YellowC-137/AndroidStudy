package com.example.busschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.data.Schedule
import com.example.busschedule.data.ScheduleDao
import kotlinx.coroutines.flow.Flow

class ViewModel(private val scheduleDao: ScheduleDao) : androidx.lifecycle.ViewModel() {
    fun getSchedules(): Flow<List<Schedule>> = scheduleDao.getAll()
    fun getBusStop(stop: String): Flow<List<Schedule>> = scheduleDao.getByStop(stop)
}

class ScheduleFactory(private val scheduleDao: ScheduleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.busschedule.ViewModel::class.java)) {
            return com.example.busschedule.ViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}