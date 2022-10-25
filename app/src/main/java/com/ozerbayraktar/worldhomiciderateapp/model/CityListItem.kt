package com.ozerbayraktar.worldhomiciderateapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CityListItem(

    @ColumnInfo(name="City")
    @SerializedName("City")
    val City: String,
    @ColumnInfo(name="Country")
    @SerializedName("Country")
    val Country: String,
    @ColumnInfo(name="Homicides (2019)")
    @SerializedName("Homicides (2019)")
    val Homicides : String,
    @ColumnInfo(name="Homicides per 100,000")
    @SerializedName("Homicides per 100,000")
    val Homicidesper: String,
    @ColumnInfo(name="Population (2019)")
    @SerializedName("Population (2019)")
    val Population : String,
    @ColumnInfo(name="Rank")
    @SerializedName("Rank")
    val Rank: String,
    ){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}
