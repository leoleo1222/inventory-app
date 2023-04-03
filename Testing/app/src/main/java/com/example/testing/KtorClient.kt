package com.example.myapplication

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.example.myapplication.KtorClient.httpClient
import com.example.testing.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// This is 1st-level feedback.
@Serializable
data class LoginResponse(
    val firstname: String,
    val last_name: String,
    val token: String,
)

var first_name: String = ""
var last_name: String = ""
var Logined: Boolean = false
var keywordHistory : String = ""
var inventoryType : String = ""

object KtorClient {
    private var token: String = ""

    val httpClient = HttpClient {
        install(ContentNegotiation) {
                json (Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                )
        }
        install(Logging)
        defaultRequest {
            // Token-based version:
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", token)
        }
        expectSuccess = true
    }

    suspend fun login(testing: LoginRequest): String{
        var message : String
        var response: LoginResponse? = null

        try{
            // Token-based
            Log.d("\tLOGIN Debug", "TESTING...")
            response = httpClient.post("https://comp4107.herokuapp.com/user/login") {
                setBody(testing)
            }.body()

            first_name = response?.firstname.toString()
            last_name = response?.last_name.toString()
            token = response?.token.toString()
            Logined = true

            message = "Login Successfully !\nToken: " + token
            Log.d("Login response", message)

        }catch(e: Exception){
            message = "Login Failed !"
            Log.d("Login Error", e.toString())
        }
        return message
    }

    suspend fun borrowBook(itemID : String) : String? {

        var response: String? = null
        try{
            // Token-based
            Log.d("\tBorrow Debug", "TESTING...")
            response = httpClient.post("https://comp4107.herokuapp.com/user/borrow/" + itemID) {
//                setBody()
            }.body()

            Log.d("Borrow response", response.toString())

        }catch(e: Exception){
            response = "Borrow Failed"
            Log.d("Borrow Error", e.toString())
        }

        return response
    }

    suspend fun returnBook(itemID : String) : String? {

        var response: String? = null
        try{
            // Token-based
            Log.d("\tReturn Debug", "TESTING...")
            response = httpClient.post("https://comp4107.herokuapp.com/user/return/" + itemID) {
//                setBody()
            }.body()

            Log.d("Return response", response.toString())

        }catch(e: Exception){
            response = "Return Failed"
            Log.d("Return Error", e.toString())
        }

        return response
    }


    suspend fun conusmeItem (itemID : String) : String? {
        var response: String? = null
        try{
            Log.d("\tConsume Debug", "TESTING...")
            response = httpClient.post("https://comp4107.herokuapp.com/user/consume/" + itemID) {
            }.body()

            Log.d("Consume response", response.toString())

        }catch(e: Exception){
            response = "Consume Failed"
            Log.d("Consume Error", e.toString())
        }

        return response
    }
    suspend fun getInventory(type : String?, keyword : String?): List<Inventory> {
        var testUrl : String
        if(type != "") {
            testUrl = "https://comp4107.herokuapp.com/inventory?type=" + type
        } else {
            testUrl = "https://comp4107.herokuapp.com/inventory?keyword=" + keyword
        }
        Log.d("testUrl", testUrl)
        return httpClient.get(testUrl).body()
    }

    suspend fun getInventoryDetail(itemID: String): Inventory {
        val testUrl = "https://comp4107.herokuapp.com/inventory/" + itemID
        Log.d("testUrl", testUrl)
        return httpClient.get(testUrl).body()
    }


}
