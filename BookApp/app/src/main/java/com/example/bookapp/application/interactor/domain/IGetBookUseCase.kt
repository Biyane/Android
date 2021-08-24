package com.example.bookapp.application.interactor.domain

import com.example.bookapp.data.database.Book

interface IGetBookUseCase {
    suspend fun getByBookId(bookId: Int): Book
}