package com.gideon.dogify.repository

import com.gideon.dogify.model.Breed
import com.gideon.dogify.db.DogifyDatabase
import com.gideon.dogify.util.DispatcherProvider
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList

internal class BreedsLocalSource(
    database: DogifyDatabase,
    private val dispatcherProvider: DispatcherProvider
) {
    private val dao = database.breedsQueries
    val breeds = dao.selectAll()
        .asFlow()
        .mapToList(dispatcherProvider.io)
        .map { breeds ->
            breeds.map { breed ->
                Breed(breed.name, breed.imageUrl, breed.isFavourite ?: false)
            }
        }

    suspend fun selectAll() =
        withContext(dispatcherProvider.io) {
            dao.selectAll { name, imageUrl, isFavourite ->
                Breed(name, imageUrl, isFavourite ?: false)
            }.executeAsList()
        }

    suspend fun insert(breed: Breed) =
        withContext(dispatcherProvider.io) {
            dao.insert(breed.name, breed.imageUrl, breed.isFavourite)
        }

    suspend fun update(breed: Breed) =
        withContext(dispatcherProvider.io) {
            dao.update(breed.imageUrl, breed.isFavourite, breed.name)
        }

    suspend fun clear() =
        withContext(dispatcherProvider.io) {
            dao.clear()
        }
}