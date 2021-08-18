package com.example.bookapp.data.network

import com.example.bookapp.core.Mapper
import com.example.bookapp.data.database.Book
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDTO(

    val title: String,
    @SerialName("authors")
    val authors: List<String> = listOf("no authors"),
    @SerialName("averageRating")
    val averageRating: Float = 0f,
    @SerialName("description")
    var description: String = "No Description",
    val imageLinks: ImageLinksDTO,
) : Mapper<Book> {

    override fun map(): Book = Book(
        title = title,
        authors = authors.joinToString(),
        averageRating = averageRating,
        description = description,
        imageLink = imageLinks.thumbnail,
    )
}

@Serializable
data class ImageLinksDTO(
    val thumbnail: String,
    val smallThumbnail: String
)

@Serializable
data class ItemsDTO(
    val items: List<VolumeInfoDTO>?
)

@Serializable
data class VolumeInfoDTO(
    @SerialName("volumeInfo")
    val books: BookDTO
)
