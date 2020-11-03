package com.lianda.news.utils.di.featuremodule

import com.lianda.news.domain.repository.CityRepository
import com.lianda.news.data.repository.CityRepositoryImpl
import com.lianda.news.domain.usecase.contract.CityUseCase
import com.lianda.news.domain.usecase.implementation.CityUseCaseImpl
import com.lianda.news.presentation.viewmodel.CityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cityModule = module {
    single<CityRepository> {
        CityRepositoryImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    single<CityUseCase> { CityUseCaseImpl(get()) }

    viewModel { CityViewModel(get()) }
}