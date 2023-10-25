package com.vitorbranco.bellalee.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.vitorbranco.bellalee.data.local.Product
import com.vitorbranco.bellalee.data.remote.ProductDto
import com.vitorbranco.bellalee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductAdapter()
    private val viewModel by lazy {
        ProductViewModel.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val rvProducts = binding.recyclerViewProducts
        rvProducts.adapter = adapter


        viewModel.productLiveData.observe(this) { productListDto ->
            val productList = productListDto.map {  productDto ->
                Product(
                    brand = productDto.brand,
                    name = productDto.name,
                    price = productDto.price,
                    apiFeaturedImage = productDto.apiFeaturedImage
                )
            }
            productList.forEachIndexed { index, product ->
                Log.d("Vitor API", "Product $index - ${product.name}, ${product.brand}, ${product.apiFeaturedImage}")
            }
            adapter.submitList(productList)
        }

//        val productFakeList = mutableListOf<Product>()
//
//        val product1 = Product(
//            "colourpop",
//            "Blotted Lip",
//            "$5.50",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/047/original/open-uri20180708-4-e7idod?1531087336"
//        )
//        val product2 = Product(
//            "boosh",
//            "Lipstick",
//            "$26.00",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/044/original/data?1531071233"
//        )
//        val product3 = Product(
//            "deciem",
//            "Serum Foundation",
//            "$6.70",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/043/original/open-uri20180706-4-nszgw9?1530919194"
//        )
//        val product4 = Product(
//            "zorah biocosmetiques",
//            "Liquid Liner",
//            "$0.00",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/041/original/open-uri20180630-4-1huiv9y?1530390387"
//        )
//        val product5 = Product(
//            "colourpop",
//            "Blotted Lip",
//            "$5.50",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/047/original/open-uri20180708-4-e7idod?1531087336"
//        )
//        val product6 = Product(
//            "boosh",
//            "Lipstick",
//            "$26.00",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/044/original/data?1531071233"
//        )
//        val product7 = Product(
//            "deciem",
//            "Serum Foundation",
//            "$6.70",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/043/original/open-uri20180706-4-nszgw9?1530919194"
//        )
//        val product8 = Product(
//            "zorah biocosmetiques",
//            "Liquid Liner",
//            "$0.00",
//            "https://s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/041/original/open-uri20180630-4-1huiv9y?1530390387"
//        )
//
//        productFakeList.add(product1)
//        productFakeList.add(product2)
//        productFakeList.add(product3)
//        productFakeList.add(product4)
//        productFakeList.add(product5)
//        productFakeList.add(product6)
//        productFakeList.add(product7)
//        productFakeList.add(product8)
//
//        adapter.submitList(productFakeList)
    }
}