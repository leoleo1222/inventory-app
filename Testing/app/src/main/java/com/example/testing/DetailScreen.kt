package com.example.testing

import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showInventoryDetailScreen(navController: NavHostController, id: String, snackbarHostState: SnackbarHostState){
    var refreshState by remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()

    val inventoryItem by produceState(
        initialValue = Inventory(),
        producer = {
            value = KtorClient.getInventoryDetail(id)
        },

        key1 = refreshState

    )

    Scaffold(
        topBar = {
                 TopAppBar(title = {Text("Details")},
                 navigationIcon = {
                     IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                     }
                 }
                 )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = inventoryItem.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxSize()
                        .padding(start = 16.dp)
                )

                when(inventoryItem.type){
                    "book" -> {
                        Text(
                            text = "ID: ${inventoryItem._id}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Title: ${inventoryItem.title}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Author: ${inventoryItem.author}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Year: ${inventoryItem.year}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "ISBN: ${inventoryItem.isbn}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Description: ${inventoryItem.description}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Category: ${inventoryItem.category}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Publisher: ${inventoryItem.publisher}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Location: ${inventoryItem.location}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remark: ${inventoryItem.remark}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Borrower: ${inventoryItem.borrower}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        inventoryType = inventoryItem.type!!
                    }

                    "game" -> {
                        Text(
                            text = "ID: ${inventoryItem._id}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Title: ${inventoryItem.title}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Description: ${inventoryItem.description}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Category: ${inventoryItem.category}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Publisher: ${inventoryItem.publisher}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Location: ${inventoryItem.location}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remark: ${inventoryItem.remark}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Borrower: ${inventoryItem.borrower}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        inventoryType = inventoryItem.type!!
                    }

                    "gift" -> {
                        Text(
                            text = "ID: ${inventoryItem._id}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Title: ${inventoryItem.title}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Description: ${inventoryItem.description}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Category: ${inventoryItem.category}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Publisher: ${inventoryItem.publisher}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Location: ${inventoryItem.location}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remark: ${inventoryItem.remark}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Amount: ${inventoryItem.remaining}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        inventoryType = inventoryItem.type!!
                    }

                    "material" -> {
                        Text(
                            text = "ID: ${inventoryItem._id}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Title: ${inventoryItem.title}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Description: ${inventoryItem.description}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Category: ${inventoryItem.category}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Location: ${inventoryItem.location}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remark: ${inventoryItem.remark}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Amount: ${inventoryItem.remaining}",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        inventoryType = inventoryItem.type!!
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    when(inventoryItem.type){
                        "book", "game" -> {
                            if(!Logined){
                                Spacer(Modifier.width(8.dp))
                                Button(
                                    onClick = {  },
                                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                                ){
                                    Text(text = "Please Sign In to Borrow/Return")
                                }
                            }

                            if(inventoryItem.borrower == "none" && Logined == true){
                                Spacer(Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        // Add your action here
                                        coroutineScope.launch {
                                            Log.d("refreshState", refreshState.toString())
                                            var response = KtorClient.borrowBook(inventoryItem._id!!)
                                            if (response != null) {
                                                snackbarHostState.showSnackbar(response)
                                            }
                                            refreshState += 1
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                                ) {
                                    Text("Borrow", color = Color.White)
                                }
                            }

                            if(inventoryItem.borrower == "me" && Logined == true){
                                Spacer(Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        // Add your action here
                                        coroutineScope.launch {
                                            Log.d("refreshState", refreshState.toString())
                                            var response = KtorClient.returnBook(inventoryItem._id!!)
                                            if (response != null) {
                                                snackbarHostState.showSnackbar(response)
                                            }
                                            refreshState += 1
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Red)
                                ) {
                                    Text("Return", color = Color.White)
                                }
                            }
                        }

                        "gift" ,"material" -> {
                            if(!Logined){
                                Spacer(Modifier.width(8.dp))
                                Button(
                                    onClick = {  },
                                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                                ){
                                    Text(text = "Please Sign In to Borrow/Return")
                                }
                            }

                            if(Logined == true && inventoryItem.remaining!! > 0){
                                Spacer(Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        // Add your action here
                                        coroutineScope.launch {
                                            Log.d("refreshState", refreshState.toString())
                                            var response = KtorClient.conusmeItem(inventoryItem._id!!)
                                            if (response != null) {
                                                snackbarHostState.showSnackbar(response)
                                            }
                                            refreshState += 1
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                                ) {
                                    Text("Consume", color = Color.Blue)
                                }
                            }
                        }
                    }

                }
            }
        }
    )

}
