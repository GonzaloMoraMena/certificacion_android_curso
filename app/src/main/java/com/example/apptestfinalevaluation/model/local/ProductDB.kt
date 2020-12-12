package com.example.apptestfinalevaluation.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATA_BASE_NAME = "product_db"

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDB : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDB? = null

        fun getDatabase(context: Context): ProductDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ProductDB::class.java,
                    DATA_BASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}