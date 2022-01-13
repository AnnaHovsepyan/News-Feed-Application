package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.model.NewsFeedPost
import com.example.myapplication.repository.NewsFeedFromNetworkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class NewsFeedViewModel(
    repository: NewsFeedFromNetworkRepository
) : ViewModel() {
    private val _newsFeedPagingData = repository.getNewsFeedPagingData()
        .cachedIn(viewModelScope)
    val newsFeedPagingData: Flow<PagingData<NewsFeedPost>> = _newsFeedPagingData
}
