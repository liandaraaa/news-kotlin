package com.lianda.news.data.api.remote

import com.lianda.news.base.BaseDataSourceApi
import com.lianda.news.data.api.entities.CitySourceApi
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CityApi {

    @POST("regional/kota/go/listing")
    @FormUrlEncoded
    suspend fun fetchCity(@FieldMap param:HashMap<String,String>) : Response<BaseDataSourceApi<List<CitySourceApi>>>


}