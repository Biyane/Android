package com.example.bookapp

import android.app.Application
import com.example.bookapp.database.BookRoomDatabase
import com.example.bookapp.repository.BookDetailRepository

class BookApplication : Application() {

    val database: BookRoomDatabase by lazy { BookRoomDatabase.getDatabase(this) }

    val repository by lazy { BookDetailRepository(database.getDao()) }
}