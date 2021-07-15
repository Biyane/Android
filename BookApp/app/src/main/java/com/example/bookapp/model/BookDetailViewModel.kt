package com.example.bookapp.model

import android.util.Log
import androidx.lifecycle.*
import com.example.bookapp.database.Book
import com.example.bookapp.network.BookApi
import com.example.bookapp.repository.BookDetailRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class BookDetailViewModel(private val repository: BookDetailRepository) : ViewModel() {

    private val _bookJson = MutableLiveData<Book>()
    val bookJson: LiveData<Book> = _bookJson

    //added code
    val bookList: LiveData<List<Book>> = repository.books.asLiveData()

    fun insertBook() = viewModelScope.launch {
        repository.insertBook()
    }
    //end


    init {
        getBookJson()
    }

    private fun getBookJson() {
        viewModelScope.launch {
            try {
                val jsonStringResult = BookApi.retrofitService.getJson("harry")
                parseJsonString(jsonStringResult)
            } catch (e: Exception){
                Log.d("BookDetailViewModel", e.toString())
            }

        }
    }

    private fun parseJsonString(jsonStr: String) {
        val root = JSONObject(jsonStr)
        val volumeInfo = root.getJSONArray("items").getJSONObject(0)
            .getJSONObject("volumeInfo")
        if (volumeInfo !is JSONException) {
            _bookJson.value = Gson().fromJson(volumeInfo.toString(), Book::class.java)
        } else {
            _bookJson.value = Book()
            bookJson.value?.title?.let { Log.d("BookViewModel1", it) }
        }
    }

}

class BookDetailViewModelFactory(
    private val repository: BookDetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return BookDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}