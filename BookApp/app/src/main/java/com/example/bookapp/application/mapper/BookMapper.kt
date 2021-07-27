package com.example.bookapp.application.mapper

import com.example.bookapp.data.database.Book
import com.example.bookapp.data.database.BookDTO

class BookMapper : Mapper<BookDTO, Book> {
    override fun map(input: BookDTO): Book = Book(
        title = input.title,
        authors = input.authors.joinToString(),
        averageRating = input.averageRating,
        description = input.description,
        imageLink = input.imageLinks.thumbnail
    )
}