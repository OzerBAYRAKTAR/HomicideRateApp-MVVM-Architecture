package com.ozerbayraktar.worldhomiciderateapp.data.apiservice

import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem
import io.reactivex.Single
import retrofit2.http.GET



interface HomicidesApi {

    @GET("2GI3px/_cities_by_homicide_rate?rapidapi-key=031e1dca85msh90130086a1967f1p199e29jsnf7777cbb3898")
    fun getHomicideList(): Single<List<CityListItem>>

}