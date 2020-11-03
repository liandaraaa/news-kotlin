package com.lianda.news.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lianda.news.domain.entities.City

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    val id:String,
    val name:String
){

    fun toCity():City{
        return City(
            id = id,
            name = name
        )
    }
}