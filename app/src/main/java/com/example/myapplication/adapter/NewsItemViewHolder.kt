package com.example.myapplication.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.NewsFeedPost

class NewsItemViewHolder(view: View, private val listener: OnCheckBoxClickListener) :
    RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val category: TextView = view.findViewById(R.id.category)
    private val checkBox: CheckBox = view.findViewById(R.id.s_checkBox)

    private var newsFeedPost: NewsFeedPost? = null

    init {
        title.setOnClickListener {
            newsFeedPost?.webUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(newsFeedPost: NewsFeedPost?) {
        this.newsFeedPost = newsFeedPost
        newsFeedPost?.let {
            title.text = it.webTitle
            category.text = it.sectionName
            checkBox.setOnClickListener { _ ->
                listener.onCheckBoxClick(checkBox.isChecked, it)
            }
            if (listener.isFavorite(it.id))
                checkBox.isChecked = true
        }
    }

    companion object {
        fun create(parent: ViewGroup, listener: OnCheckBoxClickListener): NewsItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.new_feed_item, parent, false)
            return NewsItemViewHolder(view, listener)
        }
    }
}