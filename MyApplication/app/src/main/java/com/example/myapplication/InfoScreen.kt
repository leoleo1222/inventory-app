package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

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
//    InfoDayTheme {
        Column {
//            InfoGreeting()
//            PhoneList()
            //InfoScreen(snackbarHostState)
        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBack(snackbarHostState: SnackbarHostState) {
    val padding = 16.dp
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            maxLines = 1,
            value = message,
            onValueChange = { message = it }
        )
        Spacer(Modifier.size(padding))

        Button(onClick = {
            coroutineScope.launch {
                val stringBody: String = KtorClient.postFeedback(message)
                snackbarHostState.showSnackbar(stringBody)
            }
        }) {
            Text(text = "Submit feedback")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneList() {
    val ctx = LocalContext.current

    Column {
        Contact.data.forEach { message ->
            ListItem(
                headlineText = { Text(message.office) },
                modifier = Modifier.clickable {
                    val u = Uri.parse("tel:" + message.tel)
                    val i = Intent(Intent.ACTION_DIAL, u)
                    ctx.startActivity(i)
                },
                leadingContent = {
                    Icon(
                        Icons.Filled.Call,
                        contentDescription = null
                    )
                },
                trailingContent = { Text(message.tel) }
            )
        }
    }
}

data class Contact(val office: String, val tel: String) {
    companion object {
        val data = listOf(
            Contact(office = "Admission Office", tel = "3411-2200"),
            Contact(office = "Emergencies", tel = "3411-7777"),
            Contact(office = "Health Services Center", tel = "3411-7447")
        )
    }
}

@Composable
fun InfoScreen(snackbarHostState: SnackbarHostState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InfoGreeting()
        PhoneList()
        SettingList()
        FeedBack(snackbarHostState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingList() {

    val dataStore = UserPreferences(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()

    val checked by dataStore.getMode.collectAsState(initial = false)

    ListItem(
        headlineText = { Text("Dark Mode") },
        leadingContent = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = null
            )
        },
        trailingContent = {
            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo" },
//                checked = checked,
                checked = checked ?: true,
                onCheckedChange = {
//                    checked = it
                    coroutineScope.launch {
                        dataStore.saveMode(it)
                    }
                })
        }
    )
}