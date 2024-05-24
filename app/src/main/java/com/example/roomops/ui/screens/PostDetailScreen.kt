package com.example.roomops.ui.screens

import android.renderscript.ScriptIntrinsicBLAS.UNIT
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomops.ui.PostsEvent
import com.example.roomops.ui.PostsViewModel

@Composable
fun PostDetailScreen(itemId: Int, modifier: Modifier = Modifier) {
    val viewModel: PostsViewModel = hiltViewModel()
    val currentPost by viewModel.currentPost.collectAsState(initial = null)

    Log.d("POST_ID", "PostDetailScreen: $itemId")
    LaunchedEffect(itemId) {
        viewModel.onEvent(PostsEvent.GetPost(itemId))
    }

    LaunchedEffect(currentPost?.id) {
        viewModel.onEvent(PostsEvent.ToggleFavorite)
    }


    Column(modifier = modifier.fillMaxSize()) {
        if (currentPost == null) {
            PostDetailError()
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize(1f)
                    .padding(16.dp),
            ) {
                Text(
                    text = currentPost!!.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(text = currentPost!!.body)
                Spacer(modifier = modifier.height(16.dp))
                Box(modifier = modifier.align(Alignment.End)) {
                    if (currentPost!!.isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = modifier.clickable(
                                onClick = {
                                    viewModel.onEvent(PostsEvent.ToggleFavorite)
                                }
                            )
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = modifier.clickable(
                                onClick = {
                                    viewModel.onEvent(PostsEvent.ToggleFavorite)
                                }
                            )
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun PostDetailError() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Invalid post id")
    }
}