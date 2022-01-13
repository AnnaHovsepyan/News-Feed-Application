package com.example.myapplication.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.model.NewsFeedPost

class NewsAdapter(private val listener: OnCheckBoxClickListener) :
    PagingDataAdapter<NewsFeedPost, NewsItemViewHolder>(POST_COMPARATOR) {
    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder.create(parent, listener)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<NewsFeedPost>() {
            override fun areContentsTheSame(oldItem: NewsFeedPost, newItem: NewsFeedPost): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: NewsFeedPost, newItem: NewsFeedPost): Boolean =
                oldItem.id == newItem.id
        }
    }
}