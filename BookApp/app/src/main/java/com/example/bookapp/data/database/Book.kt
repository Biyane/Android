package com.example.bookapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "authors") val authors: String,
    @ColumnInfo(name = "average_rating") val averageRating: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_link") val imageLink: String
)


