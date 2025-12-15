package com.gideon.dogify.di


import com.gideon.dogify.repository.BreedsRepository
import com.gideon.dogify.usecase.FetchBreedsUseCase
import com.gideon.dogify.usecase.GetBreedsUseCase
import com.gideon.dogify.usecase.ToggleFavouriteStateUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object KoinHelper : KoinComponent {
    fun getBreedsRepository(): BreedsRepository = get()
    fun getGetBreedsUseCase(): GetBreedsUseCase = get()
    fun getFetchBreedsUseCase(): FetchBreedsUseCase = get()
    fun getToggleFavouriteStateUseCase(): ToggleFavouriteStateUseCase = get()
}