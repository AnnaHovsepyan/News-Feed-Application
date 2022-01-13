package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class NewsFeedPost(
    @PrimaryKey
    val id: String,
    @SerializedName("webTitle")
    @ColumnInfo(name = "webTitle")
    val webTitle: String,
    @SerializedName("webUrl")
    @ColumnInfo(name = "webUrl")
    val webUrl: String,
    @SerializedName("sectionName")
    @ColumnInfo(name = "sectionName")
    val sectionName: String
)