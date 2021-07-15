package com.example.busschedule.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.busschedule.database.Schedule
import com.example.busschedule.repository.BusScheduleRepository

class BusScheduleViewModel(private val repository: BusScheduleRepository) : ViewModel() {

    val fullSchedule: LiveData<List<Schedule>> = repository.fullSchedule.asLiveData()
    fun filteredSchedule(name: String) = repository.filterSchedule(name)

}


class BusScheduleViewModelFactory(
    private val repository: BusScheduleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}