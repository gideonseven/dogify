package com.gideon.dogify.usecase

import com.gideon.dogify.model.Breed
import com.gideon.dogify.repository.BreedsRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * Forces a remote fetch and refreshes local DB.
 */
class FetchBreedsUseCase : KoinComponent {

    private val breedsRepository: BreedsRepository = get()

    @NativeCoroutines
    suspend operator fun invoke(): List<Breed> = breedsRepository.fetch()
}