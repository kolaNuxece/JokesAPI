package com.example.lernenapijokes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lernenapijokes.DB.DatabaseHelper
import com.example.lernenapijokes.DB.JokeRepository
import com.example.lernenapijokes.DataCless.FavoriteJoke
import com.example.lernenapijokes.Retrofit.RetrofitRespoce
import com.example.lernenapijokes.ui.theme.LernenAPIJokesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: JokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = JokeAdapter(emptyList()) { joke ->
            viewModel.deleteJoke(joke.id.toInt())
        }

        val rvFavorites = findViewById<RecyclerView>(R.id.rvFavorites)
        rvFavorites.layoutManager = LinearLayoutManager(this)
        rvFavorites.adapter = adapter





        val btnAdd = findViewById<Button>(R.id.addButton)
        val btnLoad = findViewById<Button>(R.id.btnLoad)
        val txtJoke = findViewById<TextView>(R.id.txtJoke)

        val db = DatabaseHelper(this)
        val repo = JokeRepository(db)

        val factory = JokeViewModelFactory(
            RetrofitRespoce.api,  // <-- API
            repo                  // <-- Repository
        )

        viewModel = ViewModelProvider(this, factory)
            .get(JokeViewModel::class.java)

        lifecycleScope.launch {
            viewModel.joke.collect { joke ->
                txtJoke.text = joke
            }
        }

        btnAdd.setOnClickListener {
            val text = txtJoke.text.toString()
            if (text.isNotEmpty()) {
                viewModel.addToFavorites(text) // все ок
                Toast.makeText(this, "Zu Favoriten hinzugefügt", Toast.LENGTH_SHORT).show()
            }
        }

        btnLoad.setOnClickListener {
            viewModel.loadJoke()
        }


        viewModel.favoriteJoke.observe(this) { list ->
            adapter.update(list)
        }
    }
}