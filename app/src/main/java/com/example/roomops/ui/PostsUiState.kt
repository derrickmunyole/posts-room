package com.example.roomops.ui

import androidx.compose.runtime.MutableState
import com.example.roomops.db.Posts

data class PostsUiState(
    val posts: List<Posts> = emptyList(),
    var title: MutableState<String>? = null,
    var post: MutableState<String>? = null
)
