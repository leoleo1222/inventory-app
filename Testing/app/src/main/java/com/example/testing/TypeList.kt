package com.example.testing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.KtorClient
//import com.example.myapplication.KtorClient.httpClient
//import com.example.myapplication.getInventory
import io.ktor.client.call.*
import io.ktor.client.request.*

data class InventoryType(val typeName: String) {
    companion object {
        val data = listOf(
            InventoryType("Books"),
            InventoryType("Games"),
            InventoryType("Gifts"),
            InventoryType("Materials")
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeScreen(navController: NavHostController) {

    Column(Modifier.padding(16.dp)) {
        Text("Select an inventory type:")
        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            InventoryType.data.chunked(2).forEach { rowItems ->
                Column(Modifier.weight(1f)) {
                    rowItems.forEach { inventoryType ->
                        Button(
                            onClick = { navController.navigate("type/${inventoryType.typeName}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
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
fun TypeNav(navController: NavHostController, inventory: List<Inventory>) {
    NavHost(
        navController = navController,
        startDestination = "type",
    ) {
        composable("type") {
            Column() {
                TypeScreen(navController)
//                var result = KtorClient.httpClient.get("https://comp4107.herokuapp.com/inventory").body()
                InventoryScreen(inventory)
            }
        }

        composable("type/{typeName}") {
            TypeScreen(navController)
        }
    }
}
