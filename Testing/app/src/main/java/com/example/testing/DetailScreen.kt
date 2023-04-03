package com.example.testing

import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.KtorClient
import com.example.myapplication.Logined
import com.example.myapplication.inventoryType
import kotlinx.coroutines.launch

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

    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.Start) {

//        AsyncImage(
//            model = book.image,
//            contentDescription = null
//        )

        when(inventoryItem.type){
            "book" -> {
                Text("ID: ${inventoryItem._id}")
                Text("Title: ${inventoryItem.title}")
                Text("Author: ${inventoryItem.author}")
                Text("Year: ${inventoryItem.year}")
                Text("ISBN: ${inventoryItem.isbn}")
                Text("Description: ${inventoryItem.description}")
                Text("Category: ${inventoryItem.category}")
                Text("Publisher: ${inventoryItem.publisher}")
                Text("Location: ${inventoryItem.location}")
                Text("Remark: ${inventoryItem.remark}")
                inventoryType = inventoryItem.type!!
            }
            "game" -> {
                Text("ID: ${inventoryItem._id}")
                Text("Title: ${inventoryItem.title}")
                Text("Description: ${inventoryItem.description}")
                Text("Category: ${inventoryItem.category}")
                Text("Publisher: ${inventoryItem.publisher}")
                Text("Location: ${inventoryItem.location}")
                Text("Remark: ${inventoryItem.remark}")
                inventoryType = inventoryItem.type!!
            }

            "gift" -> {
                Text("ID: ${inventoryItem._id}")
                Text("Title: ${inventoryItem.title}")
                Text("Description: ${inventoryItem.description}")
                Text("Category: ${inventoryItem.category}")
                Text("Publisher: ${inventoryItem.publisher}")
                Text("Location: ${inventoryItem.location}")
                Text("Remark: ${inventoryItem.remark}")
                Text("Amount: ${inventoryItem.remaining}")
                inventoryType = inventoryItem.type!!
            }

            "material" -> {
                Text("ID: ${inventoryItem._id}")
                Text("Title: ${inventoryItem.title}")
                Text("Description: ${inventoryItem.description}")
                Text("Category: ${inventoryItem.category}")
                Text("Location: ${inventoryItem.location}")
                Text("Remark: ${inventoryItem.remark}")
                Text("Amount: ${inventoryItem.remaining}")
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
