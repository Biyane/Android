package com.example.bookapp.database

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "None",
    @ColumnInfo(name = "author") val authors: List<String> = listOf(),
    @ColumnInfo(name = "average_rating") val averageRating: Float = 3.5f,
    @ColumnInfo(name = "description") val description: String = "None",
    @Embedded val imageLinks: ImageLinks = ImageLinks("None", "None")
)

data class ImageLinks(
    @ColumnInfo(name = "small_thumbnail") val smallThumbnail: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)

class Converter {
        @TypeConverter
        fun saveList(list: List<String>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        fun restoreList(str: String): List<String> {
            return Gson().fromJson(str, object : TypeToken<List<String>>() {}.type)
        }
}
