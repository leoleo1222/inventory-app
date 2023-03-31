package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val items = listOf("Inventory")
    val snackbarHostState = remember { SnackbarHostState() }
    var type by remember { mutableStateOf("book") }
    val inventoryBook = produceState(
        initialValue = listOf<InventoryItem>(),
        producer = {
            value = getInventoryItems(type)
        },
        key1 = type
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
                },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Button(
                            onClick = { type = "book" },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Book")
                        }
                        Button(
                            onClick = { type = "game" },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Game")
                        }
                        Button(
                            onClick = { type = "gift" },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Gift")
                        }
                        Button(
                            onClick = { type = "material" },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Material")
                        }
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
                    0 -> FeedScreen(inventoryBook.value)
                }
            }
        }
    )
}