package com.example.lernenapijokes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lernenapijokes.DB.JokeRepository
import com.example.lernenapijokes.DataCless.FavoriteJoke
import com.example.lernenapijokes.DataCless.JokeResponse
import com.example.lernenapijokes.Retrofit.JokeApi
import com.example.lernenapijokes.Retrofit.RetrofitRespoce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JokeViewModel(
    private val api: JokeApi,
    private val repository: JokeRepository
) : ViewModel() {

    private val _joke = MutableStateFlow<String>("")
    val joke: StateFlow<String> = _joke

    private val _favoriteJokes = MutableLiveData<List<FavoriteJoke>>()
    val favoriteJoke: LiveData<List<FavoriteJoke>> = _favoriteJokes

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        _favoriteJokes.value = repository.getFavorites()
    }

    fun addToFavorites(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addJoke(text)
            val updated = repository.getFavorites()
            withContext(Dispatchers.Main) {
                _favoriteJokes.value = updated
            }
        }
    }

    fun deleteJoke(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeJoke(id)
            val updated = repository.getFavorites()
            withContext(Dispatchers.Main) {
                _favoriteJokes.value = updated
            }
        }
    }

    fun loadJoke() {
        viewModelScope.launch {
            try {
                val response = api.getRandomJoke()
                _joke.value = response.value
            } catch (e: Exception) {
                _joke.value = "Помилка: не вдалося завантажити жарт."
            }
        }
    }
}