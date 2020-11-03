package com.lianda.news.domain.repository

import com.lianda.news.domain.entities.City
import com.lianda.news.utils.common.ResultState

interface CityRepository {

    fun isHasNetwork():Boolean

    fun isLogin():Boolean

    fun retrieveToken() : String

    suspend fun fetchCity(param:HashMap<String,String>) : ResultState<List<City>>

    suspend fun fetchSavedCity() : ResultState<List<City>>

}