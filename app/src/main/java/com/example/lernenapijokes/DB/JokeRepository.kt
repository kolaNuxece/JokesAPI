package com.example.lernenapijokes.DB

import com.example.lernenapijokes.DataCless.FavoriteJoke

class JokeRepository(private val db: DatabaseHelper)  {

    fun addJoke(text: String) {
        db.insertJoke(text)
    }

    fun removeJoke(id: Int) {
        db.deleteJoke(id.toString())
    }

    fun getFavorites(): List<FavoriteJoke> {
        return db.getAllFavorites()
    }
}