package com.example.bookapp.application.interactor.network

interface IGetBookListUseCase {
    suspend fun getBooksJson(bookName: String)
}