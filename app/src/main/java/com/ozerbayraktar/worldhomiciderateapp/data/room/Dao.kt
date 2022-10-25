package com.ozerbayraktar.worldhomiciderateapp.data.room

import androidx.room.Insert
import androidx.room.Query
import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem


@androidx.room.Dao
interface Dao {

    @Insert
    suspend fun insertAll(vararg homicides:CityListItem) : List<Long>

    //List<Long> ->primary keys
    // vargarg -> multiple homicides object

    //tüm datayı al
    @Query("Select * FROM citylistitem")
    suspend fun getAllHomicides() : List<CityListItem>


    // id ye göre itemları alma
    @Query("SELECT * FROM citylistitem WHERE uuid= :homicideId ")
    suspend fun getHomicide(homicideId:Int) :CityListItem


    @Query("DELETE  FROM citylistitem ")
    suspend fun deleteAllHomicides()



}