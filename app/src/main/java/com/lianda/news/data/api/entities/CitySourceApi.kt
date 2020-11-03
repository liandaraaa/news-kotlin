package com.lianda.news.data.api.entities

import com.google.gson.annotations.SerializedName
import com.lianda.news.data.db.entities.CityEntity
import com.lianda.news.domain.entities.City

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

    fun toCityEntity():CityEntity{
        return CityEntity(
            id = id.orEmpty(),
            name = name.orEmpty()
        )
    }
}