package com.example.bookapp.core.di

import com.example.bookapp.BookApplication
import com.example.bookapp.application.interactor.domain.GetBookUseCase
import com.example.bookapp.application.interactor.network.GetBookListListUseCase
import org.koin.dsl.module


val bookModule = module {
    single { GetBookListListUseCase(BookApplication.instance.repository) }

    single { GetBookUseCase(BookApplication.instance.repository) }
}