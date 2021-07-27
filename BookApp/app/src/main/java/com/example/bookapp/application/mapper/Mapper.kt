package com.example.bookapp.application.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}