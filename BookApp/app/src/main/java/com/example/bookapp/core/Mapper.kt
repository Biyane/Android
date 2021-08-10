package com.example.bookapp.core

interface Mapper<O> {
    fun map(): O
}