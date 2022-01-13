package com.example.myapplication.adapter

import com.example.myapplication.model.NewsFeedPost

interface OnCheckBoxClickListener {
fun onCheckBoxClick(checked: Boolean, newsFeedPost: NewsFeedPost)
fun isFavorite(id: String): Boolean
}
