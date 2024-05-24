package com.example.roomops.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Posts(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val title: String,
    val body: String,
    val isFavorite: Boolean
)
