package com.example.myapplication.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.example.myapplication.model.NewsFeedPost
import com.example.myapplication.src.NewsFeedApi
import retrofit2.HttpException
import java.io.IOException

class NewsFeedPagingSource(
    private val newsFeedApi: NewsFeedApi
) : PagingSource<Int, NewsFeedPost>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsFeedPost> {
        return try {
            val position = params.key ?: 1
            val data = newsFeedApi.getNewsList(
                page = position,
                apiKey = "test",
            ).response

            Page(
                data = data.results,
                prevKey = if (position < 2) null else position - 1,
                nextKey = if (data.results.isEmpty()) null else position + 1
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
