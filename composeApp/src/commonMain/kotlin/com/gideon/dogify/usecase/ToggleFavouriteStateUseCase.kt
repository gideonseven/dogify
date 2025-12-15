package com.gideon.dogify.usecase

import com.gideon.dogify.model.Breed
import com.gideon.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * Toggles favourite state (or updates a breed) in local DB.
 */
class ToggleFavouriteStateUseCase: KoinComponent {

    private val breedsRepository: BreedsRepository = get()

    suspend operator fun invoke(breed: Breed){
        breedsRepository.update(breed.copy(isFavourite = !breed.isFavourite))
    }
}