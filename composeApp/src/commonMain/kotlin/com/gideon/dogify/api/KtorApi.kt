package com.gideon.dogify.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal abstract class KtorApi {

    val client = httpClient

    /**
     * Use this method for configuring the request url
     */
    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom("https://dog.ceo")
            path("api", path)
        }
    }
}

private val jsonConfiguration get() = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(jsonConfiguration)
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}