package com.gideon.dogify.repository

import com.gideon.dogify.model.Breed
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.get as koinGet

class BreedsRepository : KoinComponent {
    private val remoteSource: BreedsRemoteSource = koinGet()
    private val localSource: BreedsLocalSource = koinGet()

    @NativeCoroutines
    val breeds: Flow<List<Breed>> = localSource.breeds


    @NativeCoroutines
    suspend fun get(): List<Breed> = with(localSource.selectAll()) {
        if (isNullOrEmpty()) {
            return@with fetch()
        } else {
            this
        }
    }

    @NativeCoroutines
    suspend fun fetch(): List<Breed> = supervisorScope {
        remoteSource.getBreeds().map {
            async { Breed(name = it, imageUrl = remoteSource.getBreedImage(it)) }
        }.awaitAll().also {
            localSource.clear()
            it.map { async { localSource.insert(it) } }.awaitAll()
        }
    }

    @NativeCoroutines
    suspend fun update(breed: Breed): Unit {
        localSource.update(breed)
    }
}