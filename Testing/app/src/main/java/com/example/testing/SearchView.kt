package com.example.testing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchView(onSearch = { keyword ->
        // Handle search action here
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(onSearch: (String) -> Unit) {
    var keyword by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSearch(keyword) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }
    }
}