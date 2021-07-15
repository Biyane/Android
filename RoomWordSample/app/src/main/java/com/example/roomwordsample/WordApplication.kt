package com.example.roomwordsample

import android.app.Application
import com.example.roomwordsample.database.WordRoomDatabase
import com.example.roomwordsample.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {

    private val application = CoroutineScope(SupervisorJob())
    val database by lazy { WordRoomDatabase.getDatabase(this, application) }
    val repository by lazy { WordRepository(database.wordDao()) }
}