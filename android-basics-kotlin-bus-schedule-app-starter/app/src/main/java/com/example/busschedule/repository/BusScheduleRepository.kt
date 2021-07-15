package com.example.busschedule.repository

import com.example.busschedule.database.Schedule
import com.example.busschedule.database.ScheduleDao
import kotlinx.coroutines.flow.Flow

class BusScheduleRepository(private val dao: ScheduleDao) {

     val fullSchedule: Flow<List<Schedule>>  = dao.getAll()

     fun filterSchedule(name: String) = dao.getByStopName(name)

}