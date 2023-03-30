package com.example.testing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testing.ui.theme.TestingTheme
import kotlinx.serialization.Serializable

@Serializable
data class Inventory(
    val id: Int,
    val image: String?,
    val title: String,
    val author: String?,
    val isbn: String?,
    val description: String?,
    val publisher: String?,
    val location: String,
    val remark: String,
    val type: String,
    val borrower: String?,
    val quantity: Int?,
    val remaining: Int?,
    val donatedBy: String?,
    val unitPrice: Int,
    ) {

    companion object {
        val data = listOf(
            Inventory(
                id = 1,
                image = "",
                title = "Sample Data",
                author = "",
                isbn = "",
                description = "",
                publisher = "",
                location = "",
                remark = "",
                type = "Game",
                borrower = "",
                quantity = 0,
                remaining = 0,
                unitPrice = 0,
                donatedBy = ""
            ),
            Inventory(
                id = 2,
                image = "",
                title = "Sample Data2",
                author = "",
                isbn = "",
                description = "",
                publisher = "",
                location = "",
                remark = "",
                type = "Book",
                borrower = "",
                quantity = 0,
                remaining = 0,
                unitPrice = 0,
                donatedBy = ""
            ),
        )
    }
}


@Composable
fun InventoryScreen(inventoryItems: List<Inventory>) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(inventoryItems) { inventoryItem ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                                if (inventoryItem.id % 2 == 0) Color.White else Color.LightGray
                        )
                        .clickable { /* Handle item click */ }
                ) {
                    Text(
                        text = inventoryItem.title,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = inventoryItem.type,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun InventoryPreview() {
    TestingTheme {
        InventoryScreen(Inventory.data)
    }
}