package com.example.bookapp.application.interactor.domain

import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.database.Book

class GetBookUseCase(
    private val repository: BookRepository
) : IGetBookUseCase {
    override suspend fun getByBookId(bookId: Int): Book =
        repository.getByBookId(bookId)
}