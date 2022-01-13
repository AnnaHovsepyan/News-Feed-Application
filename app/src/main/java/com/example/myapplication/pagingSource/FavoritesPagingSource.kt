package com.example.myapplication.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.example.myapplication.db.NewsFeedDb
import com.example.myapplication.model.NewsFeedPost
import retrofit2.HttpException
import java.io.IOException

class FavoritesPagingSource(
    private val newsFeedDb: NewsFeedDb
) : PagingSource<Int, NewsFeedPost>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsFeedPost> {
        return try {
            val data = newsFeedDb.newsFeedDao().getAllNewsByFavorites()

            Page(
                data = data,
                prevKey = params.key,
                nextKey = params.key
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsFeedPost>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
