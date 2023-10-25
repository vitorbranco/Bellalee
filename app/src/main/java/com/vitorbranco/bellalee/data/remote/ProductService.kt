package com.vitorbranco.bellalee.data.remote

import com.vitorbranco.bellalee.data.remote.ProductResponse
import retrofit2.http.GET

interface ProductService {

    // URL: https://makeup-api.herokuapp.com/api/v1/products.json

    @GET("api/v1/products.json")
    suspend fun fetchProducts(): List<ProductDto>

}