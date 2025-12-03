package com.gideon.dogify.repository

import com.gideon.dogify.api.model.Breed
import com.gideon.dogify.db.DogifyDatabase
import com.gideon.dogify.util.DispatcherProvider
import kotlinx.coroutines.withContext

internal class BreedsLocalSource(
    database: DogifyDatabase,
    private val dispatcherProvider: DispatcherProvider
) {
    private val dao = database.breedsQueries
    val breeds = dao.selectAll().asFlow().mapToList()
        .map{ breeds ->breeds.map {
            Breed(it.name, it.imageUrl, it.isFavourite ?: false)
        }}

    suspend fun selectAll () =
        withContext(dispatcherProvider.io){
            dao.selectAll { name, imageUrl, isFavourite ->
                Breed(name, imageUrl, isFavourite ?: false)
            }.executeAsList()
        }
    suspend fun insert (breed: Breed =
        withContext(dispatcherProvider.io){
            dao.insert(breed.name, breed.imageUrl, breed.isFavourite)
        })

    suspend fun update (breed: Breed) =
        withContext(dispatcherProvider.io){
            dao.update(breed.imageUrl, breed.isFavourite, breed.name)
        }

    suspend fun clear() =
        withContext(dispatcherProvider.io){
            dao.clear()
        }
}