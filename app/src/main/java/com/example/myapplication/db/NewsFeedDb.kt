package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.NewsFeedPost

@Database(
    entities = [NewsFeedPost::class],
    version = 1,
    exportSchema = false
)
abstract class NewsFeedDb : RoomDatabase() {
    companion object {
        fun create(context: Context): NewsFeedDb {
            val databaseBuilder =
                Room.databaseBuilder(context, NewsFeedDb::class.java, "newsfeed.db")
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun newsFeedDao(): NewsFeedDao
}