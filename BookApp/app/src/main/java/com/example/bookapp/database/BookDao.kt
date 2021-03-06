package com.example.bookapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * from book_table")
    fun getAllBooks(): Flow<List<Book>>

    @Insert
    suspend fun addBook()

}