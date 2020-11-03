package com.lianda.news.domain.usecase.contract

import com.lianda.news.domain.entities.City
import com.lianda.news.utils.common.ResultState

interface CityUseCase {

    suspend fun fetchCity(): ResultState<List<City>>
}