package com.example.roomops

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomops.ui.PostsUiState
import com.example.roomops.ui.SnackbarControllerImpl
import com.example.roomops.ui.screens.AddPostScreen
import com.example.roomops.ui.screens.PostDetailScreen
import com.example.roomops.ui.screens.PostListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsApp() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = SnackbarControllerImpl(snackbarHostState, rememberCoroutineScope())

    /**
     LaunchedEffect tracks changes in the navController's current back stack entry.
     Whenever the back stack entry changes,
     the currentRoute variable is updated with the route of the new current destination.
     **/
    LaunchedEffect(navController.currentBackStackEntryFlow) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route?:""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Posts")
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            Log.d("Current Route:", "Current Route: $currentRoute")
            when (currentRoute) {
                "home" -> {
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("addPost") },
                    ) {
                        Icon(Icons.Filled.Add, null)
                        Text("Add post")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController, startDestination = "home") {
                composable("home") { PostListScreen(navController) }
                composable("details/{itemId}",
                    arguments = listOf(navArgument("itemId") { type = NavType.IntType })
                    ) { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getInt("itemId")
                    Log.d("TO POST DETAILS", "itemId: $itemId")
                    if (itemId != null) {
                        PostDetailScreen(itemId)
                    }
                }
                composable("addPost") { AddPostScreen(snackbarController = snackbarController)}
            }
        }
    }
}
