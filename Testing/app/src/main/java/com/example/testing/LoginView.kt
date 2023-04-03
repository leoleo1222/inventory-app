package com.example.testing

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.myapplication.token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String?,
    val password: String?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(snackbarHostState: SnackbarHostState) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var WelcomeText by remember { mutableStateOf("Please Sign In") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = WelcomeText, fontSize = 30.sp)

        InfoGreeting()
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                //akenworth1@prlog.org
                //123456
                Log.d("Debug", email)
                Log.d("Debug", password)
                coroutineScope.launch {
                    var data = LoginRequest(email, password)
                    var stringBody: String = KtorClient.login(data)
                    WelcomeText = "Welcome Back \n\n" + first_name + " " + last_name
                    snackbarHostState.showSnackbar(stringBody)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Login")
        }
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

