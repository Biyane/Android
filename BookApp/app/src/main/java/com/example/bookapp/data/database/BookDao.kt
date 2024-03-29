package com.example.bookapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<Book>>

    @Insert
    suspend fun addBook(book: Book)

    @Query("SELECT * FROM book_table WHERE id == :id")
    suspend fun getBookById(id: Int): Book

    @Query("DELETE FROM book_table WHERE id == :id")
    suspend fun deleteBookById(id: Int)

}