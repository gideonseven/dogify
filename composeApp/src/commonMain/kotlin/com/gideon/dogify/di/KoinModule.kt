package com.gideon.dogify.di

import com.gideon.dogify.api.BreedsApi
import com.gideon.dogify.repository.BreedRemoteSource
import com.gideon.dogify.repository.BreedsRepository
import com.gideon.dogify.usecase.FetchBreedsUseCase
import com.gideon.dogify.usecase.GetBreedsUseCase
import com.gideon.dogify.usecase.ToggleFavouriteStateUseCase
import com.gideon.dogify.util.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val apiModule = module {
    factory { BreedsApi() }
}

private val repositoryModule = module {
    single { BreedsRepository(get()) }
    factory { BreedRemoteSource(get(), get()) }
}

private val useCaseModule = module {
    factory { GetBreedsUseCase() }
    factory { FetchBreedsUseCase() }
    factory { ToggleFavouriteStateUseCase() }
}

private val sharedModules = listOf(useCaseModule, repositoryModule, apiModule, utilityModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModules)
}