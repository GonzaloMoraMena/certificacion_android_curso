package com.example.apptestfinalevaluation.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: String,
    val image: String,
    val description: String? = "",
    val lastPrice: Int? = 0,
    val credit: Boolean? = false

)