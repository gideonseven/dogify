package com.gideon.dogify.di

import com.gideon.dogify.usecase.FetchBreedsUseCase
import com.gideon.dogify.usecase.GetBreedsUseCase
import com.gideon.dogify.usecase.ToggleFavouriteStateUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val useCaseModule = module {
    factory { GetBreedsUseCase() }
    factory { FetchBreedsUseCase() }
    factory { ToggleFavouriteStateUseCase() }
}

private val sharedModules = listOf(useCaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModules)
}