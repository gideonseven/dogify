package com.gideon.dogify.usecase

import com.gideon.dogify.api.model.Breed
import com.gideon.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchBreedsUseCase : KoinComponent {
    private val breedsRepository: BreedsRepository by inject()

    suspend fun invoke(): List<Breed> = breedsRepository.fetch()
}