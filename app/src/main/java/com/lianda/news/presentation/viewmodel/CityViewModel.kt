package com.lianda.news.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lianda.news.domain.entities.City
import com.lianda.news.domain.usecase.contract.CityUseCase
import com.lianda.news.utils.common.ResultState
import kotlinx.coroutines.launch

class CityViewModel (private val useCase: CityUseCase): ViewModel() {

    fun fetchCity(): MutableLiveData<ResultState<List<City>>>{
        val fetchCity = MutableLiveData<ResultState<List<City>>>()

        viewModelScope.launch {
            val cityResponse = useCase.fetchCity()
            fetchCity.value = cityResponse
        }

        return fetchCity
    }
}