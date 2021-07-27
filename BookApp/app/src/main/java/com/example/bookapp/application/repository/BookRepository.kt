package com.example.bookapp.application.repository

import com.example.bookapp.data.database.Book
import com.example.bookapp.data.database.BookDao
import kotlinx.coroutines.flow.Flow


class BookRepository(private val dao: BookDao) {

    val books: Flow<List<Book>> = dao.getAllBooks()

    suspend fun insertBook(book: Book) = dao.addBook(book)


}