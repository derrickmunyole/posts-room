package com.example.roomops.ui

import com.example.roomops.db.Posts

sealed interface PostsEvent {
    data object GetPosts : PostsEvent
    data class AddPost(val post: Posts) : PostsEvent
    data class DeletePost(val post: Posts) : PostsEvent
    data class GetPost(val id: Int) : PostsEvent
    data object ToggleFavorite : PostsEvent
}