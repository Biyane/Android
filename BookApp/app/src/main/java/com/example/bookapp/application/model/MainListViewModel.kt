package com.example.bookapp.application.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.database.Book

class MainListViewModel(
    repository: BookRepository
) : ViewModel() {

    val bookList: LiveData<List<Book>> = repository.books.asLiveData()
}

class MainListViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}