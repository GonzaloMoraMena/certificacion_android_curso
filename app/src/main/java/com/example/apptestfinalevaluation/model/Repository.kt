package com.example.apptestfinalevaluation.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.apptestfinalevaluation.model.local.ProductDao
import com.example.apptestfinalevaluation.model.local.ProductEntity
import com.example.apptestfinalevaluation.model.remote.ClientRetrofit
import com.example.apptestfinalevaluation.model.remote.ProductDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val product: ProductDao) {
    private val service = ClientRetrofit.getRetrofitClient()
    val allProductLiveData = product.getAllProductDB()


    fun getProductById(id: String): LiveData<ProductEntity> {
        return product.getProductById(id)
    }

    fun callApiProducts() {
        val getProducts = service.getAllProducts()
        getProducts.enqueue(object : Callback<List<ProductDetail>> {
            override fun onFailure(call: Call<List<ProductDetail>>, t: Throwable) {
                Log.e("Repository", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<ProductDetail>>,
                response: Response<List<ProductDetail>>
            ) {
                when (response.code()) {
                    in 200..299 -> CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.let {
                            for (item: ProductDetail in response.body()!!) {
                                Log.d("insert", item.toString())
                                callApiByIdProducts(item.id)
                            }
                        }
                    }
                    in 300..399 -> Log.d("ERROR 300", response.errorBody().toString())
                }

            }

        })
    }

    fun callApiByIdProducts(id: String) {
        val getProducts = service.getDetailProduct(id)
        getProducts.enqueue(object : Callback<ProductDetail> {

            override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                Log.e("Repository", t.message.toString())
            }

            override fun onResponse(call: Call<ProductDetail>, response: Response<ProductDetail>) {
                when (response.code()) {
                    in 200..299 -> CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.let {
                            Log.d("insert", it.toString())
                            product.insertOneProduct(transformDetailToEntity(it))
                        }
                    }
                    in 300..399 -> Log.d("ERROR 300", response.errorBody().toString())
                }


            }


        })
    }


    fun transformDetailToEntity(product: ProductDetail): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            price = product.price,
            description = product.description,
            lastPrice = product.lastPrice,
            credit = product.credit,
            image = product.image
        )
    }


}


