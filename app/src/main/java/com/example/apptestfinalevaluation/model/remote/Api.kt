package com.example.apptestfinalevaluation.model.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("products")
    fun getAllProducts(): Call<List<ProductDetail>>

    //Coroutines
    @GET("products")
    suspend fun fetchAllProductsWithCoroutines(): Response<List<ProductDetail>>

    @GET("details/{id}")
    fun getDetailProduct(@Path("id") id: String): Call<ProductDetail>
}