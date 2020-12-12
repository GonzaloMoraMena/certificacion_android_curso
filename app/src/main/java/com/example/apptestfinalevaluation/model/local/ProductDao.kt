package com.example.apptestfinalevaluation.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM product order by name")
    fun getAllProductDB(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE id =:id")
    fun getProductById(id: String): LiveData<ProductEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneProduct(productEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllProduct(productEntity: List<ProductEntity>)
}