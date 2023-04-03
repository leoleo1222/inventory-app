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
import com.example.myapplication.KtorClient
import com.example.testing.ui.theme.TestingTheme
import kotlinx.serialization.Serializable

@Serializable
data class Inventory(
    val _id: String? = null,
    val title: String?= null,
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
){
    companion object{
        val data = listOf(
            "1234",
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            "1925",
            "9781781396847",
            "The story primarily concerns the young and mysterious millionaire Jay Gatsby and his quixotic passion and obsession for the beautiful former debutante Daisy Buchanan.",
            "Classic Literature",
            "Charles Scribner's Sons",
            "New York",
            "https://example.com/great-gatsby.jpg",
            "",
            "Book",
            "",
            5,
            "John Doe",
            50,
            10,
            5
        )
    }
}

@Composable
fun InventoryScreen(type: String, keyword: String, navController: NavController) {
    val inventory = produceState(
        initialValue = listOf<Inventory>(),
        producer = {
//            value = KtorClient.getInventory(id)
            when(type){
                "" -> value = KtorClient.getInventory("", keyword)
                "book" -> value = KtorClient.getInventory("book", "")
                "game" -> value = KtorClient.getInventory("game", "")
                "gift" -> value = KtorClient.getInventory("gift", "")
                "material" -> value = KtorClient.getInventory("material", "")
                else -> value = KtorClient.getInventory("", "")
            }
        }
    )

    Column(Modifier.padding(horizontal = 6.dp)) {
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
                            when(inventoryItem.type) {
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
