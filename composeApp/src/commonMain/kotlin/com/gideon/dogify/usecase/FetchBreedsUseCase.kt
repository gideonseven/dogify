package com.gideon.dogify.usecase

import com.gideon.dogify.api.model.Breed

class FetchBreedsUseCase {
    suspend fun invoke(): List<Breed> = listOf(Breed("Test fetch", ""))
}