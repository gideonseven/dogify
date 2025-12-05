package com.gideon.dogify.api

import BreedsResponse
import com.gideon.dogify.api.model.BreedImageResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class BreedsApi : KtorApi() {
    suspend fun getBreeds(): BreedsResponse = client.get {
        apiUrl("breeds/list")
    }.body()

    suspend fun getRandomBreedImageFor(breed: String): BreedImageResponse =
        client.get {
            apiUrl("breed/$breed/images/random")
        }.body()
}