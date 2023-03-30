package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

import kotlinx.serialization.Serializable
import java.util.Objects

@Serializable
data class InventoryItem(
    val _id: String,
    val title: String,
    val author: String,
    val year: String,
    val isbn: String,
    val description: String,
    val category: String,
    val publisher: String,
    val location: String,
    val image: String,
    val remark: String,
    val type: String,
    val borrower: String
) {
    companion object {
        val data = listOf(
            InventoryItem(
                _id = "641bc9fffc13ae6253000a16",
                title = "61*",
                author = "Noelyn Labden",
                year = "2006",
                isbn = "148830211-1",
                description = "Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.\n\nQuisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.",
                category = "Sales",
                publisher = "Chevrolet",
                location = "Pass",
                image = "http://dummyimage.com/113x100.png/ff4444/ffffff",
                remark = "Diverse empowering task-force",
                type = "book",
                borrower = "641c1ad3402648e069017bdb"
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(feeds: List<InventoryItem>) {
    LazyColumn {
        items(feeds) { feed ->
            Card(
                onClick = { /* Do something */ },
                // modifier = Modifier.size(width = 180.dp, height = 100.dp)
            ) {
                Column {
                    AsyncImage(
                        model = feed.image,
                        contentDescription = null
                    )
                    Box(Modifier.fillMaxSize()) {
                        Text(feed.title, Modifier.align(Alignment.Center))
                    }
                }

            }

//            Text(feed.title)
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
//    InfoDayTheme {
    FeedScreen(InventoryItem.data)
//    }
}