package com.example.apptestfinalevaluation.model.remote


data class ProductDetail(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val description: String?,
    val lastPrice: Int?,
    val credit: Boolean?

)