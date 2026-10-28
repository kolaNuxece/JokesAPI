package com.example.lernenapijokes.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.lernenapijokes.DataCless.FavoriteJoke

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "jokes.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE favorites(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            text TEXT
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS favorites")
        onCreate(db)
    }
    fun insertJoke(text: String){
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("text", text)
        }
        db.insert("favorites", null, cv)
        db.close()
    }
    fun deleteJoke(id: String) {
        val db = writableDatabase
        db.delete("favorites", "id=?", arrayOf(id))
        db.close()
    }

    fun getAllFavorites(): List<FavoriteJoke> {
        val list = mutableListOf<FavoriteJoke>()
        val db = readableDatabase
        val c = db.rawQuery("SELECT * FROM favorites", null)

        if (c.moveToFirst()) {
            do {
                list.add(
                    FavoriteJoke(
                        id = c.getString(c.getColumnIndexOrThrow("id")),
                        text = c.getString(c.getColumnIndexOrThrow("text"))
                    )
                )
            } while (c.moveToNext())
        }

        c.close()
        db.close()
        return list
    }

}