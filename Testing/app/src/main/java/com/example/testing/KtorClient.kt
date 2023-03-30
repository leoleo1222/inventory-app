package com.example.myapplication

import com.example.myapplication.KtorClient.httpClient
import com.example.testing.Inventory
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

// This is 1st-level feedback.
@Serializable
data class HttpBinResponse(
    val args: Map<String, String>,
    val data: String,
    val files: Map<String, String>,
    val form: Map<String, String>,
    val headers: Map<String, String>,
    val json: String?,
    val origin: String,
    val url: String
)

object KtorClient {
    private var token: String = ""

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json() // enable the client to perform JSON serialization
        }
        install(Logging)
        defaultRequest {

            // Token-based version:
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", "Bearer " + token)
        }
        expectSuccess = true
    }


//    suspend fun postFeedback(feedback: String): String {
//        return httpClient.post("https://httpbin.org/post") {
////            setBody(feedback)
////        }.body()
//
//        // Second-level feedback
////        val response: HttpBinResponse = httpClient.post("https://httpbin.org/post") {
////            setBody(feedback)
////        }.body()
////
////        return response.headers["X-Amzn-Trace-Id"].toString()
//
//        // Token-based
//        val response: HttpBinResponse = httpClient.post("https://httpbin.org/post") {
//            setBody(feedback)
//        }.body()
//
//        token = response.headers["X-Amzn-Trace-Id"].toString()
//        return response.toString()
//    }
//
}

suspend fun getInventory(): List<Inventory> {
    return httpClient.get("https://comp4107.herokuapp.com/inventory").body()
}