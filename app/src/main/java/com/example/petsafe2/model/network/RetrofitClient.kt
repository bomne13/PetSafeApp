package com.example.petsafe2.model.network

import com.example.petsafe2.model.network.api.UserService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://222.232.29.236:58887"
    var gson: Gson = GsonBuilder().setLenient().create()

    private val client = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getInstance(): Retrofit {
        return client
    }

}

