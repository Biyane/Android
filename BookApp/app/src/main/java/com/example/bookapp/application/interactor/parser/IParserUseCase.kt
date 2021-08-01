package com.example.bookapp.application.interactor.parser

import com.example.bookapp.data.BookDTO

interface IParserUseCase {
    fun parse(jsonString: String): BookDTO
}