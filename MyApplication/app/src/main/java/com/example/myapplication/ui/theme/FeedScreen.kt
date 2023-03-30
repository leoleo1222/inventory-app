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
    val description: String,
    val image: String,
    val category: String,
    val location: String,
    val remark: String
) {
    companion object {
        val data = listOf(
            InventoryItem(
                _id = "1",
                title = "Feed 1",
                description = "Description 1",
                image = "image1.png",
                category = "Category 1",
                location = "Location 1",
                remark = "Remark 1"
            ),
            InventoryItem(
                _id = "2",
                title = "Feed 2",
                description = "Description 2",
                image = "image2.png",
                category = "Category 2",
                location = "Location 2",
                remark = "Remark 2"
            ),
            InventoryItem(
                _id = "3",
                title = "Feed 3",
                description = "Description 3",
                image = "image3.png",
                category = "Category 3",
                location = "Location 3",
                remark = "Remark 3"
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
//    FeedScreen(InventoryItem.data)
//    }
}