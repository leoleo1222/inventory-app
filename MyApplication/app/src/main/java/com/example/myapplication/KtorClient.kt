package com.example.myapplication

import com.example.myapplication.KtorClient.httpClient
import com.example.myapplication.ui.theme.InventoryItem
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

@Serializable
data class InventoryItem(
    val _id: String,
    val title: String,
    val description: String,
    val image: String,
    val category: String,
    val location: String,
    val remark: String
)

object KtorClient {
    private var token: String = ""
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json() // enable the client to perform JSON serialization
        }
        install(Logging)
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer " + token)
        }
        expectSuccess = true
    }

    suspend fun getInventoryItems(): List<InventoryItem> {
        return try {
            httpClient.get("https://comp4107.herokuapp.com/inventory").body()
        } catch (e: Exception) {
            println("getInventoryItems Exception: $e")
            emptyList()
        }
    }
}