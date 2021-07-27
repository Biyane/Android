package com.example.bookapp.presentation.model

import androidx.lifecycle.*
import com.example.bookapp.data.database.Book
import com.example.bookapp.network.BookApi
import com.example.bookapp.repository.BookRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _bookJson = MutableLiveData<Book>()
    val bookJson: LiveData<Book> = _bookJson

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
//                insertBook(book)
            } catch (e: Exception){
                _bookJson.value = Book(title = "Something went wrong")
            }

        }
    }

    private fun parseJsonString(jsonStr: String) {
        val emptyBook = Book()
        val root = JSONObject(jsonStr)
        val volumeInfo = root.getJSONArray("items").getJSONObject(0)
            .getJSONObject("volumeInfo")
        if (volumeInfo !is JSONException) {
            _bookJson.value = Gson().fromJson(volumeInfo.toString(), Book::class.java)
        } else {
            _bookJson.value = emptyBook
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