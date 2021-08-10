package com.example.bookapp.application.repository

import com.example.bookapp.data.database.Book
import com.example.bookapp.data.database.BookDao
import com.example.bookapp.data.network.BookApi
import kotlinx.coroutines.flow.Flow


class BookRepository(private val dao: BookDao) {

    val books: Flow<List<Book>> = dao.getAllBooks()

    suspend fun insertBook(book: Book) = dao.addBook(book)

    suspend fun getBookListJson(bookName: String) = BookApi.retrofitService.getJson(bookName).items
}
