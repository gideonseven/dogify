package com.gideon.dogify.repository

import com.gideon.dogify.api.model.Breed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

class BreedsRepository internal constructor(
    private val remoteSource: BreedRemoteSource
) {
    suspend fun get() = fetch()
    suspend fun fetch() = supervisorScope {
        remoteSource.getBreeds().map {
            async {
                Breed(
                    name = it, imageUrl =
                        remoteSource.getBreedImage(it)
                )
            }
        }.awaitAll()
    }
}