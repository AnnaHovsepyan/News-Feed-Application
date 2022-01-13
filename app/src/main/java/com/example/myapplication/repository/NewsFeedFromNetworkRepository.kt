package com.example.myapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.pagingSource.NewsFeedPagingSource
import com.example.myapplication.src.NewsFeedApi

class NewsFeedFromNetworkRepository(private val newsFeedApi: NewsFeedApi) {
    fun getNewsFeedPagingData() = Pager(
        PagingConfig(100)
    ) {
        NewsFeedPagingSource(
            newsFeedApi = newsFeedApi
        )
    }.flow
}
