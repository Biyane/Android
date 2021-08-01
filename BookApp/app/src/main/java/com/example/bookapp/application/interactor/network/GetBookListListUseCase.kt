package com.example.bookapp.application.interactor.network

import android.util.Log
import com.example.bookapp.application.interactor.parser.IParserUseCase
import com.example.bookapp.application.repository.BookRepository


class GetBookListListUseCase(
    private val repository: BookRepository,
    private val parser: IParserUseCase
) : IGetBookListUseCase {
    override suspend fun getBooksJson(bookName: String) {
        try {
            val jsonStringResult = repository.getBookListJson(bookName)
//            parser.parse(jsonStringResult)
        } catch (e: Exception) {
            Log.e("bookModel", e.toString())
        }
    }
}
