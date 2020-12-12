package com.example.apptestfinalevaluation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.apptestfinalevaluation.model.local.ProductDB
import com.example.apptestfinalevaluation.model.local.ProductDao
import com.example.apptestfinalevaluation.model.local.ProductEntity
import com.example.apptestfinalevaluation.model.remote.ProductDetail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TestDao {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var productDao: ProductDao
    private lateinit var db: ProductDB

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ProductDB::class.java).build()
        productDao = db.getProductDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertOneElements() = runBlocking {
        val product: ProductEntity = ProductEntity(
            "1", "zte v8", "200"
            , "--", "ceclular primer modelo", 300, false
        )
        productDao.insertOneProduct(product)

        productDao.getProductById("1").observeForever{
            assertThat(it).isNotNull()
            println(it.toString())
            assertThat(it.name).isEqualTo("zte v8")
        }
    }

}