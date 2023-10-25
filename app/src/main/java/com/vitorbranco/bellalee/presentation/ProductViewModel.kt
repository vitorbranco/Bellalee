package com.vitorbranco.bellalee.presentation

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitorbranco.bellalee.data.remote.ProductDto
import com.vitorbranco.bellalee.data.remote.ProductService
import com.vitorbranco.bellalee.data.remote.RetrofitModule
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.net.SocketTimeoutException

class ProductViewModel(private val productService: ProductService) : ViewModel() {

    private val _productLiveData = MutableLiveData<List<ProductDto>>()
    val productLiveData: LiveData<List<ProductDto>> = _productLiveData

    private var retryAttempts = 0

    init {
        getProductList()
    }

    private fun getProductList() {
        val maxRetries = 3
        val initialDelay = 1000L

        viewModelScope.launch {
            for(retryAttempt in 0..maxRetries) {
                try {
                    val productList = productService.fetchProducts()
                    _productLiveData.value = productList

                    retryAttempts = 0
                    return@launch
                } catch (ex: SocketTimeoutException) {
                    if (retryAttempt < maxRetries) {
                        retryAttempts = retryAttempt + 1
                        kotlinx.coroutines.delay(initialDelay * retryAttempt)
                    } else {
                        ex.printStackTrace()
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    companion object {

        fun create(): ProductViewModel {
            val productService = RetrofitModule.createProductService()
            return ProductViewModel(productService)
        }
    }

}