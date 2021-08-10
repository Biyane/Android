package com.example.bookapp

import android.app.Application
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.core.di.bookModule
import com.example.bookapp.data.database.BookRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@BookApplication)
            modules(bookModule)
        }
    }

    companion object {
        lateinit var instance: BookApplication
            private set
    }

    val database by lazy { BookRoomDatabase.getDatabase(this) }

    val repository by lazy { BookRepository(database.getDao()) }
}
