package com.example.myapplication.viewModel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.model.NewsFeedPost
import com.example.myapplication.repository.FavoritesFromMemoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repositoryFavorites: FavoritesFromMemoryRepository
) : ViewModel() {
    private val _result = MutableLiveData<List<String>>()
    val result: MutableLiveData<List<String>> = _result

    private val _newsPagingData: Flow<PagingData<NewsFeedPost>> =
        repositoryFavorites.getFavoriteNewsPagingData()
            .cachedIn(viewModelScope)
    val newsPagingData: Flow<PagingData<NewsFeedPost>> = _newsPagingData

    fun insert(post: NewsFeedPost) = viewModelScope.launch {
        repositoryFavorites.insert(post)
    }

    fun remove(id: String) = viewModelScope.launch {
        repositoryFavorites.remove(id)
    }

    fun isFavorite() {
        viewModelScope.launch {
            val isFavorite = repositoryFavorites.isFavorite()
            _result.value = isFavorite
        }
    }
}
