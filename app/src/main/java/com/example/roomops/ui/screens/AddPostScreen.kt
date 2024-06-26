package com.example.roomops.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomops.db.Posts
import com.example.roomops.ui.PostsEvent
import com.example.roomops.ui.PostsUiState
import com.example.roomops.ui.PostsViewModel
import com.example.roomops.ui.SnackbarController
import com.example.roomops.ui.SnackbarControllerImpl
import com.example.roomops.ui.UiEvent

@Composable
fun AddPostScreen(
    modifier: Modifier = Modifier,
    snackbarController: SnackbarController,
    viewModel: PostsViewModel = hiltViewModel()
) {
    var titleText by remember { mutableStateOf("") }
    var postText by remember { mutableStateOf("") }
    val uiEvents by viewModel.uiEvents.collectAsState(initial = null)

    LaunchedEffect(uiEvents) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarController.showMessage(
                        message = event.message,
                        actionLabel = null,
                        withDismissAction = false,
                        duration = SnackbarDuration.Short,
                        onSnackbarResult = {}
                    )
                }
                // Handle other events as needed
                UiEvent.NavigateToHome -> TODO()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text("Create your post",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = modifier.height(48.dp))
            Text(
                text = "Title",
                modifier = modifier
                    .align(Alignment.Start),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                value = titleText,
                onValueChange = { newText -> titleText = newText },
                maxLines = 2,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "Post",
                modifier = modifier
                    .align(Alignment.Start),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                value = postText,
                onValueChange = { newText -> postText = newText },
                maxLines = 5,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(16.dp))
            ElevatedButton(onClick = {
                val post = Posts(
                    title = titleText,
                    body = postText
                )
                viewModel.onEvent(PostsEvent.AddPost(Posts(null, post.title, post.body)))
            }) {
                Text("Submit")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun AddPostScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create your post")
        Text(
            text = "Title",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 18.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = "",
            onValueChange = {},
            maxLines = 2,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Post",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 18.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = "",
            onValueChange = {},
            maxLines = 5,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(onClick = {}) {
            Text("Submit")
        }
    }
}