package com.gideon.dogify.repository

import com.gideon.dogify.api.BreedsApi
import com.gideon.dogify.util.DispatcherProvider
import kotlinx.coroutines.withContext

internal class BreedRemoteSource(
    private val api: BreedsApi,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun getBreeds() =
        withContext(dispatcherProvider.io) {
            api.getBreeds().breeds
        }

    suspend fun getBreedImage(breed: String) =
        withContext(dispatcherProvider.io) {
            api.getRandomBreedImageFor(breed).breedImageUrl
        }
}