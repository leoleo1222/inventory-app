package com.example.testing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class InventoryType(val typeName: String) {
    companion object {
        val data = listOf(
            InventoryType(typeName = "Books"),
            InventoryType(typeName = "Games"),
            InventoryType(typeName = "Gifts"),
            InventoryType(typeName = "Materials")
        )
    }
}

@Composable
fun InfoGreeting() {
    val padding = 16.dp
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.size(padding))
        Image(
            painter = painterResource(id = R.drawable.hkbu_logo),
            contentDescription = stringResource(id = R.string.hkbu_logo),
            modifier = Modifier.size(180.dp)
        )
        Spacer(Modifier.size(padding))
        Text(text = "HKBU InfoDay App", fontSize = 30.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun InfoPreview() {
//    {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        InfoGreeting()
        InventoryList()
    }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryList() {
    Column {
        InventoryType.data.forEach { message ->
            ListItem(
                headlineText = { Text(message.typeName) },
                leadingContent = {
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = null
                    )
                },
                trailingContent = { Text(message.typeName) }
            )
        }
    }
}