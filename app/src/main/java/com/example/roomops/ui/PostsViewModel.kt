package com.example.roomops.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomops.db.Posts
import com.example.roomops.db.PostsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val dao: PostsDao
) : ViewModel() {
    // Initialize _posts with an empty list of Posts
    private val _posts = MutableStateFlow<List<Posts>>(emptyList())
    val posts = _posts.asStateFlow()
    private val _currentPost = MutableStateFlow<Posts?>(null)
    val currentPost: StateFlow<Posts?> get() = _currentPost

    //Ui Events
    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents: SharedFlow<UiEvent> get() = _uiEvents


    fun onEvent(event: PostsEvent) {
        when (event) {
            is PostsEvent.AddPost -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        dao.upsertPost(event.post)
                        _uiEvents.emit(UiEvent.ShowSnackBar(message = "Post added"))
                    } catch (e: Exception) {
                        throw Exception("Error inserting post", e)
                    }
                }
            }

            is PostsEvent.DeletePost -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        dao.deletePost(event.post)
                        _uiEvents.emit(UiEvent.ShowSnackBar(message = "Post deleted"))
                        _uiEvents.emit(UiEvent.NavigateToHome)
                    } catch (e: Exception) {
                        throw Exception("Error deleting post", e)
                    }
                }
            }

            is PostsEvent.GetPosts -> {
                viewModelScope.launch {
                    dao.getAllPosts().collect { posts ->
                        _posts.value = posts
                    }
                }
            }

            is PostsEvent.GetPost -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val post = dao.getPostItem(event.id)
                    _currentPost.emit(post)
                }
            }

            PostsEvent.ToggleFavorite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val post = currentPost.value
                    if (post != null) {
                        val updatedPost = post.copy(isFavorite = !post.isFavorite)
                        dao.upsertPost(updatedPost)
                        _currentPost.emit(updatedPost)
                    }

                }
            }
        }
    }
}