package com.ozerbayraktar.worldhomiciderateapp.data.apiservice

import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem
import com.ozerbayraktar.worldhomiciderateapp.util.Constants.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class HomicideApiService {

    private val api= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(HomicidesApi::class.java)

    fun getData(): Single<List<CityListItem>> {
        return api.getHomicideList()
    }


}