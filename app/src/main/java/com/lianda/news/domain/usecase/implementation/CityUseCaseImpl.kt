package com.lianda.news.domain.usecase.implementation

import com.lianda.news.domain.entities.City
import com.lianda.news.domain.repository.CityRepository
import com.lianda.news.domain.usecase.contract.CityUseCase
import com.lianda.news.utils.common.ResultState

class CityUseCaseImpl (private val repository: CityRepository):CityUseCase{
    override suspend fun fetchCity(): ResultState<List<City>> {
        val param = HashMap<String, String>()
        param["token"] = "noauth.android"

        return if (repository.isHasNetwork()){
            repository.fetchCity(param)
        }else{
            repository.fetchSavedCity()
        }
    }

}