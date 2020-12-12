package com.example.apptestfinalevaluation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.apptestfinalevaluation.model.Repository
import com.example.apptestfinalevaluation.model.local.ProductDB
import com.example.apptestfinalevaluation.model.local.ProductEntity

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository

    init {
        val productDao = ProductDB.getDatabase(application).getProductDao()
        repository = Repository(productDao)
        repository.callApiProducts()
    }

    fun exposeLiveDataFromDatabase(): LiveData<List<ProductEntity>> {
        return repository.allProductLiveData
    }

    fun productById(id: String): LiveData<ProductEntity> {
        return repository.getProductById(id)
    }
}