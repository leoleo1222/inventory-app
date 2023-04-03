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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var keyword by remember { mutableStateOf("") }
    var page by remember { mutableStateOf(1) }

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
                navController.navigate("search/${keyword}/page/t")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

//        Spacer(Modifier.height(16.dp))
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Button(
//                onClick = {
//                    if (page > 1) {
//                        page -= 1
//                    }
//                }
//            ) {
//                Text("Previous Page")
//            }
//            Button(
//                onClick = {}
//            ) {
//                Text("Page $page")
//            }
//            Button(
//                onClick = {
//                    page += 1
//                }
//            ) {
//                Text("Next Page")
//            }
//        }
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

        composable("search/{keyword}/{page}/{t}"){ backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword")
            val page = backStackEntry.arguments?.getString("page")

            keywordHistory = keyword.toString()
            Column {
                SearchScreen(navController)
                if (keyword != null) {
//                    InventoryScreen("", keyword, "1",navController)
                    InventoryScreen("", "", page!! ,navController)
                }
            }
        }

        composable("type/{type}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            Column{
                if (id != null) {
                    Column() {
                        showInventoryDetailScreen(navController, id, snackbarHostState)
//                        backtoSearchButton(navController)
                    }
                }
            }
        }
    }
}