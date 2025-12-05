package com.gideon.dogify.repository

import com.gideon.dogify.api.model.Breed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

class BreedsRepository internal constructor(
    private val remoteSource: BreedRemoteSource,
    private val localSource: BreedsLocalSource
) {
    val breeds = localSource.breeds
    internal suspend fun get() =
        with(localSource.selectAll()) {
            if (isNullOrEmpty()) {
                return@with fetch()
            } else {
                this
            }
        }

    //    suspend fun get() = fetch()
    suspend fun fetch() = supervisorScope {
        remoteSource.getBreeds().map {
            async {
                Breed(
                    name = it, imageUrl =
                        remoteSource.getBreedImage(it)
                )
            }
        }.awaitAll().also { breeds ->
            localSource.clear()
            breeds.map {
                async {
                    localSource.insert(it)
                }
            }.awaitAll()
        }
    }

    suspend fun update(breed: Breed) = localSource.update(breed)
}