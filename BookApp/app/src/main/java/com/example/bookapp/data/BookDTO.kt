package com.example.bookapp.data

import com.example.bookapp.application.mapper.BookMapper
import com.example.bookapp.data.database.Book
import com.google.gson.annotations.SerializedName

data class BookDTO(
    val title: String,
    val authors: List<String>,
    val averageRating: Float,
    val description: String,
    val imageLinks: ImageLinksDTO
) {
    fun mapToBookEntity() = Book(
        title = title,
        authors = authors.joinToString(),
        averageRating = averageRating,
        description = description,
        imageLink = imageLinks.thumbnail
    )

    fun mapBookDTOToBook() = BookMapper.map(this)
}


data class ImageLinksDTO(
    val thumbnail: String,
    val smallThumbnail: String
)

data class ItemsDTO(
    @SerializedName("items")
    val items: List<VolumeInfoDTO>
)

data class VolumeInfoDTO(
    @SerializedName("volumeInfo")
    val books: BookDTO
)




//class BookDTODeserializer : JsonDeserializer<BookDTO> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): BookDTO {
//        json as JsonObject
//
//        val title = json.get("title").asString
//        val authors = Gson().fromJson<List<String>>(json.get("authors"), object : TypeToken<List<String>>(){}.type)
//        val averageRatingJson = json.get("averageRating")
//        val averageRating = averageRatingJson?.asFloat ?: 0.0f
//        val descriptionJson = json.get("description")
//        val description = descriptionJson?.asString ?: "No Description"
//        val imageLinks =
//            Gson().fromJson(json.get("imageLinks"), ImageLinksDTO::class.java)
//        return BookDTO(title, authors, averageRating, description, imageLinks)
//    }
//}
