package com.vitorbranco.bellalee.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    fun createProductService(): ProductService {

        val logging = HttpLoggingInterceptor()
        logging.apply {
            HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging).build()

        val retrofit = Retrofit
            .Builder().client(client).baseUrl("https://makeup-api.herokuapp.com/")
            .addConverterFactory(
                GsonConverterFactory.create(Gson())
            )

        return retrofit.build().create(ProductService::class.java)
    }
}