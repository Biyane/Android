package com.example.bookapp.repository

import com.example.bookapp.database.Book
import com.example.bookapp.database.BookDao
import kotlinx.coroutines.flow.Flow

class BookDetailRepository(private val dao: BookDao) {

    val books: Flow<List<Book>> = dao.getAllBooks()

    suspend fun insertBook() = dao.addBook()
}