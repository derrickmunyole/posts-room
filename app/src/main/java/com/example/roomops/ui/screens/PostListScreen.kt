package com.example.roomops.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.roomops.db.Posts
import com.example.roomops.ui.PostsEvent
import com.example.roomops.ui.PostsViewModel

@Composable
fun PostListScreen(navController: NavController) {
    val viewModel: PostsViewModel = hiltViewModel()
    val posts by viewModel.posts.collectAsState(initial = emptyList())
    // Refresh posts when the screen becomes visible
    LaunchedEffect(Unit) {
        viewModel.onEvent(PostsEvent.GetPosts)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if(posts.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No posts found")
            }
        } else {
            LazyColumn {
                items(posts) { postItem ->
                    PostListItem(postItem, navController)
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PostListScreenPreview() {
//    LazyColumn(modifier= Modifier.padding(vertical = 8.dp)) {
//        items(posts) {
//            PostListItem("Post $it", null)
//        }
//    }
//}


@Composable
fun PostListItem(
    post: Posts,
    navController: NavController?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clickable(onClick = {
                Log.d("Navigation", "Navigating to details with itemId: ${post.id}")
                navController?.navigate("details/${post.id}")
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )  {
        Text(
            text = post.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
        Icon(
            imageVector = if (post.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}


@Preview(widthDp = 320,showBackground = true)
@Composable
fun PostListItemPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )  {
        Text(text = "Post Title", style = MaterialTheme.typography.bodyMedium)
        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
    }
}