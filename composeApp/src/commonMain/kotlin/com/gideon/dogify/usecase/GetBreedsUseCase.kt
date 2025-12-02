package com.gideon.dogify.usecase

import com.gideon.dogify.model.Breed

class GetBreedsUseCase {
    suspend fun invoke(): List<Breed> = listOf(Breed("Test get", ""))
}