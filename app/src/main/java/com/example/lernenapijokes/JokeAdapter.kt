package com.example.lernenapijokes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lernenapijokes.DataCless.FavoriteJoke

class JokeAdapter(
    private var jokes: List<FavoriteJoke>,
    private val onDeleteClick: (FavoriteJoke) -> Unit
) : RecyclerView.Adapter<JokeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtJoke: TextView = view.findViewById(R.id.txtFavJoke)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_joke, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = jokes[position]
        holder.txtJoke.text = joke.text
        holder.btnDelete.setOnClickListener {
            onDeleteClick(joke)
        }
    }

    override fun getItemCount(): Int = jokes.size

    fun update(newList: List<FavoriteJoke>) {
        jokes = newList
        notifyDataSetChanged()
    }
}