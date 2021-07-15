package com.example.bookapp.model

import androidx.lifecycle.*
import com.example.bookapp.database.Book
import com.example.bookapp.repository.BookDetailRepository
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookDetailRepository) : ViewModel() {

    val bookList: LiveData<List<Book>> = repository.books.asLiveData()

    fun insertBook() = viewModelScope.launch {
        repository.insertBook()
    }

}

class BookViewModelFactory(
    private val repository: BookDetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}