package com.example.testing

import android.widget.Button
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import com.example.myapplication.KtorClient.httpClient
//import com.example.myapplication.getInventory
import io.ktor.client.call.*
import io.ktor.client.request.*

data class InventoryType(val typeName: String) {
    companion object {
        val data = listOf(
            InventoryType("book"),
            InventoryType("game"),
            InventoryType("gift"),
            InventoryType("material")
        )
    }
}
@Composable
fun TypeScreen(navController: NavHostController) {
    var page by remember { mutableStateOf(1) }

    Column(Modifier.padding(4.dp)) {
        if(inventoryType == ""){
            Text("Select an inventory type:")
        }
        Spacer(Modifier.height(4.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            InventoryType.data.chunked(2).forEach { rowItems ->
                Column(Modifier.weight(1f)) {
                    rowItems.forEach { inventoryType ->
                        Button(
                            onClick = { navController.navigate("type/${inventoryType.typeName}/$page/t") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 2.dp)
                        ) {
                            Text(inventoryType.typeName)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun backtoTypeButton(navController: NavHostController){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Button(
            onClick = {
                navController.navigate("type/${inventoryType}")
            },
        ) {
            Text("Back to List Items")
        }
    }
}

@Composable
fun TypeNav(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    NavHost(
        navController = navController,
        startDestination = "type",
    ) {
        composable("type") {
            Column() {
                TypeScreen(navController)
            }
        }

        composable("type/{type}/{page}/{t}") { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val page = backStackEntry.arguments?.getString("page")
            Column {
                TypeScreen(navController)
                InventoryScreen(type!!, "", page!! ,navController)
            }
        }

        composable("type/{type}/{id}") { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val id = backStackEntry.arguments?.getString("id")
            Column{
                if (id != null) {
                    Column() {
                        showInventoryDetailScreen(navController, id, snackbarHostState)
                        backtoTypeButton(navController)
                    }
                }
            }
        }
    }
}
