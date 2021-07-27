package com.example.bookapp.domain.model

import android.util.Log
import androidx.lifecycle.*
import com.example.bookapp.application.network.BookApi
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.database.Book
import com.example.bookapp.data.database.BookDTO
import com.example.bookapp.data.database.BookDeserializer
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _bookJson = MutableLiveData<BookDTO>()
    val bookJson: LiveData<BookDTO> = _bookJson

    val bookList: LiveData<List<Book>> = repository.books.asLiveData()

    fun insertBook(book: Book) = viewModelScope.launch {
        repository.insertBook(book)
    }


    init {
        getBookJson()
    }

    private fun getBookJson() {
        viewModelScope.launch {
            try {
                val jsonStringResult = BookApi.retrofitService.getJson("Hazel")
                parseJsonString(jsonStringResult)
            } catch (e: Exception){
                Log.e("bookModel", e.toString())
            }

        }
    }

    private fun parseJsonString(jsonStr: String) {
        val root = JSONObject(jsonStr)
        val volumeInfo = root.getJSONArray("items").getJSONObject(0)
            .getJSONObject("volumeInfo")
        if (volumeInfo !is JSONException) {
            val gSon = GsonBuilder().registerTypeAdapter(BookDTO::class.java, BookDeserializer()).create()
            _bookJson.value = gSon.fromJson(volumeInfo.toString(), BookDTO::class.java)
        }
    }

}

class BookViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}