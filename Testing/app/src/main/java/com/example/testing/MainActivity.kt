package com.example.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.getInventory
//import com.example.myapplication.getInventory
//import com.example.myapplication.getInventory
import com.example.testing.ui.theme.TestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    ScaffoldScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestingTheme {
//        Greeting("Android")
        ScaffoldScreen();
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    var navController = rememberNavController()
    val items = listOf("List Items", "Search Items", "Login")
    val snackbarHostState = remember { SnackbarHostState() }

//    val inventory = produceState(
//        initialValue = listOf<Inventory>(),
//        producer = {
//            value = getInventory()
//        }
//    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory Management App") }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Info, contentDescription = item) },
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
                when(selectedItem) {
                    0 -> InventoryScreen(Inventory.data)
                    1 -> SearchView(onSearch = { keyword ->
                        // Handle search action here
                    })
                    2 -> LoginScreen()
                }

            }
        }
    )
}