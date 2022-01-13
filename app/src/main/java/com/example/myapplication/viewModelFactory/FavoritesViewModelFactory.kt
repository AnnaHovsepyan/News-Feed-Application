package com.example.myapplication.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.db.NewsFeedDb
import com.example.myapplication.repository.FavoritesFromMemoryRepository
import com.example.myapplication.viewModel.FavoritesViewModel

object FavoritesViewModelFactory {
    fun create(context: Context): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
                    val repo = FavoritesFromMemoryRepository(NewsFeedDb.create(context))
                    return FavoritesViewModel(repo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}