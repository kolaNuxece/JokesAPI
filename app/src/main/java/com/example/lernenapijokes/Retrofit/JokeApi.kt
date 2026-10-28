package com.example.lernenapijokes.Retrofit

import com.example.lernenapijokes.DataCless.FavoriteJoke
import com.example.lernenapijokes.DataCless.JokeResponse
import retrofit2.http.GET

interface JokeApi {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse
}