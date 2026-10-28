package com.example.lernenapijokes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lernenapijokes.DB.JokeRepository
import com.example.lernenapijokes.Retrofit.JokeApi
import com.example.lernenapijokes.Retrofit.RetrofitRespoce

class JokeViewModelFactory(
    private val api: JokeApi,
    private val repository: JokeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JokeViewModel(api, repository) as T
    }
}