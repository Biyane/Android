package com.example.bookapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URI = "https://www.googleapis.com"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URI)
    .build()

interface BookApiService {

    @GET("books/v1/volumes")
    suspend fun getJson(@Query("q") name: String): ItemsDTO
}

object BookApi {
    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }
}