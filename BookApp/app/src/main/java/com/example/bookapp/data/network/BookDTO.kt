package com.example.bookapp.data.network

import com.example.bookapp.core.Mapper
import com.example.bookapp.data.database.Book
import com.google.gson.annotations.SerializedName


data class BookDTO(

    val title: String,
    val authors: List<String>,
    val averageRating: Float,
    var description: String?,
    val imageLinks: ImageLinksDTO,
) : Mapper<Book> {

    override fun map(): Book  = Book (
        title = title,
        authors = authors.joinToString(),
        averageRating = averageRating,
        description = description ?: "No description",
        imageLink = imageLinks.thumbnail
    )
}


data class ImageLinksDTO(
    val thumbnail: String,
    val smallThumbnail: String
)

data class ItemsDTO(
    @SerializedName("items")
    val items: List<VolumeInfoDTO>?
)

data class VolumeInfoDTO(
    @SerializedName("volumeInfo")
    val books: BookDTO
)
