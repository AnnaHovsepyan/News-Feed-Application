package com.example.myapplication.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.NewsFeedFromNetworkRepository
import com.example.myapplication.src.NewsFeedApi
import com.example.myapplication.viewModel.NewsFeedViewModel

object NewsFeedViewModelFactory {
    fun create(): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
                    val repo = NewsFeedFromNetworkRepository(NewsFeedApi.create())
                    return NewsFeedViewModel(repo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}