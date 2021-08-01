package com.example.bookapp

import android.app.Application
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.database.BookRoomDatabase

class BookApplication : Application() {

    val database by lazy { BookRoomDatabase.getDatabase(this) }

    val repository by lazy { BookRepository(database.getDao()) }
}
