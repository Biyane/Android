package com.example.bookapp.application.mapper

import com.example.bookapp.data.BookDTO
import com.example.bookapp.data.database.Book

object BookMapper : Mapper<BookDTO, Book> {
    override fun map(input: BookDTO): Book = Book(
        title = input.title,
        authors = input.authors.joinToString(),
        averageRating = input.averageRating,
        description = input.description,
        imageLink = input.imageLinks.thumbnail
    )
}