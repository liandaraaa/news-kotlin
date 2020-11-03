package com.lianda.news.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class OrderEntity(
    @PrimaryKey
    val id:Int
)