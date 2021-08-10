package com.example.bookapp.application.interactor.network

import com.example.bookapp.data.network.BookDTO

interface IGetBookListUseCase {
    suspend fun getBooksJson(bookName: String): List<BookDTO>
}