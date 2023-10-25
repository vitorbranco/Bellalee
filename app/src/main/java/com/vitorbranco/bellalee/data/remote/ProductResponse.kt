package com.vitorbranco.bellalee.data.remote

import com.google.gson.annotations.SerializedName

data class ProductResponse (
    val data: List<ProductDto>
)
data class ProductDto(
    val brand: String?,
    val name: String?,
    val price: String?,
    @SerializedName("api_featured_image")
    val apiFeaturedImage: String?
)