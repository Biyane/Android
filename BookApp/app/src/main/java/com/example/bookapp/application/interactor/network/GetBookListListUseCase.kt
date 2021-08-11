package com.example.bookapp.application.interactor.network

import android.util.Log
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.network.BookDTO


class GetBookListListUseCase(
    private val repository: BookRepository
) : IGetBookListUseCase {
    override suspend fun getBooksJson(bookName: String): List<BookDTO> =
        try {
            val volumeInfoTemp = repository.getBookListJson(bookName)
            volumeInfoTemp?.map { it.books } ?: listOf()
        }
        catch (e: Exception){
            Log.e("GetBookListUseCase Internet call", e.toString())
            listOf()
    }
}

