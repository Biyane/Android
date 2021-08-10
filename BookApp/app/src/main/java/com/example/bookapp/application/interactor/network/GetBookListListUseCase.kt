package com.example.bookapp.application.interactor.network

import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.network.BookDTO


class GetBookListListUseCase(
    private val repository: BookRepository
) : IGetBookListUseCase {
    override suspend fun getBooksJson(bookName: String): List<BookDTO> {
        val volumeInfoTemp = repository.getBookListJson(bookName)
        return volumeInfoTemp?.map { it.books } ?: listOf()
    }
}

