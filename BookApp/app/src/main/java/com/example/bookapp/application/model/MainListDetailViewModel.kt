package com.example.bookapp.application.model

import androidx.lifecycle.*
import com.example.bookapp.application.interactor.domain.IGetBookUseCase
import com.example.bookapp.data.database.Book
import kotlinx.coroutines.launch

class MainListDetailViewModel(
    private val getBook: IGetBookUseCase
) : ViewModel() {

    private var _book = MutableLiveData<Book>()
    val book: LiveData<Book> = _book

    fun getByBookId(bookId: Int) = viewModelScope.launch {
        _book.value = getBook.getByBookId(bookId)
    }
}

class MainListDetailViewModelFactory(
    private val getBook: IGetBookUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListDetailViewModel::class.java)) {
            return MainListDetailViewModel(getBook) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}