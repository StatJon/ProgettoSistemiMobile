package com.unibo.mobile.data.remote.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {
    private val baseUrl = "https://www.dnd5eapi.co/api/2014/"

    private val moshi = Moshi.Builder()
        .build()

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val dndService: DndApi by lazy {
        retrofitClient.create(DndApi::class.java)
    }

}
