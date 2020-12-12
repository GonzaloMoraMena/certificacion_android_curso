package com.example.apptestfinalevaluation

import com.example.apptestfinalevaluation.model.local.ProductEntity
import com.example.apptestfinalevaluation.model.remote.Api
import com.example.apptestfinalevaluation.model.remote.ProductDetail
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NetworkUnitTest {

    lateinit var mMockWebServer: MockWebServer
    lateinit var productApi: Api

    @Before
    fun setUp() {
        mMockWebServer = MockWebServer()
        val mRetrofit = Retrofit.Builder()
            .baseUrl(mMockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = mRetrofit.create(Api::class.java)
    }

    @After
    fun shutDown() {
        mMockWebServer.shutdown()
    }

    @Test
    fun getAllProductsSuccess() = runBlocking {
        val product: ProductDetail = ProductDetail(
            "1", "zte v8", "200"
            , "--", "ceclular primer modelo", 300, false
        )
        val product_2: ProductDetail = ProductDetail(
            "2", "zte v10", "500"
            , "--", "ceclular ultimo modelo", 500, false
        )

        val resultList = listOf<ProductDetail>(product, product_2)
        mMockWebServer.enqueue(MockResponse().setBody(Gson().toJson(resultList)))
        val result = productApi.fetchAllProductsWithCoroutines()
        assertThat(result).isNotNull()
        val body = result.body()
        assertThat(body).hasSize(2)
        assertThat(body?.get(0)?.id).isEqualTo("1")
        val request = mMockWebServer.takeRequest()
        println(request.path)
        assertThat(request.path).isEqualTo("/products")
    }
}