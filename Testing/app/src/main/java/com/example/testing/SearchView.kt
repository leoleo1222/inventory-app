package com.example.testing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.KtorClient
import com.example.myapplication.keywordHistory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var keyword by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Please enter keyword to search inventory:")
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate("search/${keyword}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }
    }
}

@Composable
fun backtoSearchButton(navController: NavHostController){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Button(
            onClick = {
                navController.navigate("search/${keywordHistory}")
            },
        ) {
            Text("Back to List Items")
        }
    }
}

@Composable
fun SearchNav(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    NavHost(
        navController = navController,
        startDestination = "search",
    ) {
        composable("search") {
            Column() {
                SearchScreen(navController)
            }
        }

        composable("search/{keyword}"){ backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword")
            keywordHistory = keyword.toString()
            Column {
                SearchScreen(navController)
                if (keyword != null) {
                    InventoryScreen("", keyword, navController)
                }
            }
        }

        composable("type/{type}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            Column{
                if (id != null) {
                    Column() {
                        showInventoryDetailScreen(navController, id, snackbarHostState)
                        backtoSearchButton(navController)
                    }
                }
            }
        }
    }
}