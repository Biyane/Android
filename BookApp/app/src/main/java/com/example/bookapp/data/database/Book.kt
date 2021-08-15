package com.example.bookapp.data.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @NonNull @ColumnInfo(name = "title") val title: String,
    @NonNull @ColumnInfo(name = "authors") val authors: String,
    @NonNull @ColumnInfo(name = "average_rating") val averageRating: Float,
    @NonNull @ColumnInfo(name = "description") val description: String,
    @NonNull @ColumnInfo(name = "image_link") val imageLink: String
)


