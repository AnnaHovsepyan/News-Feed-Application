package com.example.myapplication.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.db.NewsFeedDb
import com.example.myapplication.pagingSource.FavoritesPagingSource
import com.example.myapplication.model.NewsFeedPost

class FavoritesFromMemoryRepository(private val newsFeedDb: NewsFeedDb) {
    fun getFavoriteNewsPagingData() = Pager(
        PagingConfig(100)
    ) {
        FavoritesPagingSource(
            newsFeedDb = newsFeedDb
        )
    }.flow

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(news: NewsFeedPost) {
        newsFeedDb.newsFeedDao().addNewsToFavorites(news)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun remove(id: String) {
        newsFeedDb.newsFeedDao().deleteFromFavoritesById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun isFavorite(): List<String> {
        return newsFeedDb.newsFeedDao().isFavorite()
    }
}
