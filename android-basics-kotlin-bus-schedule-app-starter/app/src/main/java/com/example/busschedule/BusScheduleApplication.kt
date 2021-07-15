package com.example.busschedule

import android.app.Application
import com.example.busschedule.database.AppDatabase
import com.example.busschedule.repository.BusScheduleRepository

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusScheduleRepository(database.scheduleDao()) }
}