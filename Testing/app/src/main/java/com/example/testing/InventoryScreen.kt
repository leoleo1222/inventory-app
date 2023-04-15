package com.example.testing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
data class Inventory(
    val _id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val year: String? = null,
    val isbn: String? = null,
    val description: String? = null,
    val category: String? = null,
    val publisher: String? = null,
    val location: String? = null,
    val image: String? = null,
    val remark: String? = null,
    val type: String? = null,
    val borrower: String? = null,
    val quantity: Int? = null,
    val donatedBy: String? = null,
    val amount: Int? = null,
    val unitPrice: Int? = null,
    val remaining: Int? = null,
)

@Composable
fun InventoryScreen(type: String, keyword: String, page: String, navController: NavController) {
    var page by remember { mutableStateOf(1) }

    val inventory = produceState(
        initialValue = listOf<Inventory>(),
        producer = {
            value = when (type) {
                "" -> KtorClient.getInventory("", keyword, page.toString())
                "book" -> KtorClient.getInventory("book", "", page.toString())
                "game" -> KtorClient.getInventory("game", "", page.toString())
                "gift" -> KtorClient.getInventory("gift", "", page.toString())
                "material" -> KtorClient.getInventory("material", "", page.toString())
                else -> KtorClient.getInventory("", "", page.toString())
            }
        },
        key1 = page
    )

    Column(Modifier.padding(horizontal = 6.dp)) {
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    if (page > 1) {
                        page -= 1
                    }
                }
            ) {
                Text("Previous Page")
            }
            Button(
                onClick = {}
            ) {
                Text("Page $page")
            }
            Button(
                onClick = {
                    page += 1
                }
            ) {
                Text("Next Page")
            }
        }

        LazyColumn(Modifier.fillMaxSize()) {
            items(inventory.value) { inventoryItem ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            Color.LightGray
                        )
                        .clickable {
                            when (inventoryItem.type) {
                                "book" -> navController.navigate("type/book/${inventoryItem._id}")
                                "game" -> navController.navigate("type/game/${inventoryItem._id}")
                                "gift" -> navController.navigate("type/gift/${inventoryItem._id}")
                                "material" -> navController.navigate("type/material/${inventoryItem._id}")
                                else -> navController.navigate("type")
                            }
                        }
                ) {
                    Text(
                        text = inventoryItem.title!!,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = inventoryItem.type!!,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    )
                }
            }
        }
    }
}
