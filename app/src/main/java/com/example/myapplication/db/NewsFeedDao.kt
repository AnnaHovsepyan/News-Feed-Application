package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.NewsFeedPost

@Dao
interface NewsFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewsToFavorites(news: NewsFeedPost)

    @Query("SELECT * FROM favorites")
    suspend fun getAllNewsByFavorites(): List<NewsFeedPost>

    @Query("DELETE FROM favorites where id == :id")
    suspend fun deleteFromFavoritesById(id: String)

    @Query("SELECT id FROM favorites")
    suspend fun isFavorite(): List<String>
}
