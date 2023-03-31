package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.KtorClient.getInventoryItems
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.FeedScreen
import com.example.myapplication.ui.theme.InventoryItem


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val dataStore = UserPreferences(LocalContext.current)
            val mode by dataStore.getMode.collectAsState(initial = false)

            MyApplicationTheme(darkTheme = mode ?: false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//            Greeting("Android")
                    ScaffoldScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ScaffoldScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    var navController = rememberNavController()
    val items = listOf("Game", "Book", "Gift", "Material")
    val snackbarHostState = remember { SnackbarHostState() }
    val type by remember { mutableStateOf("game") }
    val inventoryBook = produceState(
        initialValue = listOf<InventoryItem>(),
        producer = {
            value = getInventoryItems(type)
        },
        key1 = type
    )
    val inventoryGame = produceState(
        initialValue = listOf<InventoryItem>(),
        producer = {
            value = getInventoryItems("game")
        }
    )
    val inventoryGift = produceState(
        initialValue = listOf<InventoryItem>(),
        producer = {
            value = getInventoryItems("gift")
        }
    )
    val inventoryMaterial = produceState(
        initialValue = listOf<InventoryItem>(),
        producer = {
            value = getInventoryItems("material")
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Inventory App") },
                navigationIcon = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    if (navBackStackEntry?.arguments?.getBoolean("topLevel") == false) {
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    } else {
                        null
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {
                when (selectedItem) {
                    0 -> FeedScreen(inventoryGame.value)
                    1 -> FeedScreen(inventoryBook.value)
                    2 -> FeedScreen(inventoryGift.value)
                    3 -> FeedScreen(inventoryMaterial.value)
                }
            }
        }
    )
}