package com.example.bookapp.application.model

import android.util.Log
import androidx.lifecycle.*
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.BookDTO
import com.example.bookapp.data.database.Book
import com.example.bookapp.data.network.BookApi
import kotlinx.coroutines.launch

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
                _bookJson.value = BookApi.retrofitService.getJson("Hazel").items[0].books
                bookJson.value?.let { Log.e("myPointer", it.description) }
                Log.e("helloThere", "haskjdhaj")
            } catch (e: Exception){
                Log.e("bookModel", e.toString())
            }

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