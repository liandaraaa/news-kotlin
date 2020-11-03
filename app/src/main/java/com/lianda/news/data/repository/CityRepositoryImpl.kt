package com.lianda.news.data.repository

import android.content.Context
import com.lianda.news.data.api.remote.CityApi
import com.lianda.news.data.db.dao.CityDao
import com.lianda.news.data.preference.AppPreference
import com.lianda.news.domain.entities.City
import com.lianda.news.domain.repository.CityRepository
import com.lianda.news.utils.common.ResultState
import com.lianda.news.utils.constants.PreferenceKeys.KEY_DEVICE_ID
import com.lianda.news.utils.constants.PreferenceKeys.KEY_LOGIN
import com.lianda.news.utils.constants.PreferenceKeys.KEY_TOKEN
import com.lianda.news.utils.extentions.handleApiError
import com.lianda.news.utils.extentions.handleApiSuccess
import com.lianda.news.utils.extentions.hasNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CityRepositoryImpl (private val context:Context,
                          private val api: CityApi,
                          private val preference: AppPreference,
                          private val dbCity:CityDao): CityRepository {

    override fun isHasNetwork(): Boolean {
        return hasNetwork(context)
    }

    override fun isLogin(): Boolean {
        return  preference.getBoolean(KEY_LOGIN)
    }

    override fun retrieveToken(): String {
        return  preference.getString(KEY_TOKEN)
    }

    override suspend fun fetchCity(param:HashMap<String,String>): ResultState<List<City>> {
        param["device"] = preference.getString(KEY_DEVICE_ID)

        return try {
            val response = api.fetchCity(param)
            if (response.isSuccessful){
                response.body()?.let {
                    withContext(Dispatchers.IO){
                        dbCity.insertAll(it.data?.map { it.toCityEntity() }.orEmpty())
                        handleApiSuccess(
                            data = it.data?.map { it.toCity() }.orEmpty(),
                            message = it.message.orEmpty()
                        )
                    }
                } ?: handleApiError(response.errorBody())
            }else{
                handleApiError(response.errorBody())
            }
        }catch (e: Exception){
            handleApiError(e)
        }
    }

    override suspend fun fetchSavedCity(): ResultState<List<City>> {
        val datas = dbCity.getAll()
        return if (datas.isNotEmpty()) {
            handleApiSuccess(
                data = dbCity.getAll().map { it.toCity() },
                message = "success"
            )
        }else{
            handleApiError(Exception(Throwable("Tidak ada data")))
        }
    }

}