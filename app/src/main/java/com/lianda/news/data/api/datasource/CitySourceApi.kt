package com.lianda.news.data.api.datasource

import com.google.gson.annotations.SerializedName
import com.lianda.news.domain.model.City

data class CitySourceApi(
    @SerializedName("id")
    val id:String?,
    @SerializedName("name")
    val name:String?
){

    fun toCity():City{
        return City(
            id = id.orEmpty(),
            name = name.orEmpty()
        )
    }
}