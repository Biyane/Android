package com.example.bookapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Book::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class BookRoomDatabase : RoomDatabase() {

    abstract fun getDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: BookRoomDatabase? = null

        fun getDatabase(context: Context): BookRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BookRoomDatabase::class.java,
                    "book_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }

    }

}