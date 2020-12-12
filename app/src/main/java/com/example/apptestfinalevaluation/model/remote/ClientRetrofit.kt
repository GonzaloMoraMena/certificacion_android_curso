package com.example.apptestfinalevaluation.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofit {

    companion object {
        private const val URL_BASE = "http://my-json-server.typicode.com/Himuravidal/FakeAPIdata/"
        fun getRetrofitClient(): Api {
            val mRetrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return mRetrofit.create(Api::class.java)
        }
    }
}

