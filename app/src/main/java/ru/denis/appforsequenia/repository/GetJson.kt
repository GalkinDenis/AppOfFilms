package ru.denis.appforsequenia.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetJson {
    @GET("/{path}")
    suspend fun getItemJson(@Path("path") path: String): Response<MoviesRecyclerViewItem.ItemJson>
}