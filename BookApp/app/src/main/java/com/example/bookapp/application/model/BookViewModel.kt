package com.example.bookapp.application.model

import androidx.lifecycle.*
import com.example.bookapp.application.interactor.network.IGetBookListUseCase
import com.example.bookapp.application.repository.BookRepository
import com.example.bookapp.data.database.Book
import com.example.bookapp.data.network.BookDTO
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository,
    private val getBookList: IGetBookListUseCase
) : ViewModel() {

    private var _books = MutableLiveData<List<BookDTO>>()
    val books: LiveData<List<BookDTO>> = _books

    fun insertBook(book: Book) = viewModelScope.launch {
        repository.insertBook(book)
    }

    fun getBookList(bookName: String) = viewModelScope.launch {
        _books.value = getBookList.getBooksJson(bookName)
    }
}

class BookViewModelFactory(
    private val repository: BookRepository,
    private val getBookList: IGetBookListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository, getBookList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}