package com.example.bookapp.application.model

import androidx.lifecycle.ViewModel
import com.example.bookapp.application.interactor.network.IGetBookListUseCase

class SearchBookViewModel(
    private val getBookListUseCase: IGetBookListUseCase
) : ViewModel() {

}