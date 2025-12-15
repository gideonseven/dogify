package com.gideon.dogify.usecase

import com.gideon.dogify.model.Breed
import com.gideon.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
class GetBreedsUseCase : KoinComponent {

    private val breedsRepository: BreedsRepository = get()

    suspend operator fun invoke(): List<Breed> = breedsRepository.get()
}