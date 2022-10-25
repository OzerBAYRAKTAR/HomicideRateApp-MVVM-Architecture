package com.ozerbayraktar.worldhomiciderateapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozerbayraktar.worldhomiciderateapp.data.room.HomicideDatabase
import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem
import kotlinx.coroutines.launch

class DetailViewModel(application: Application):BaseViewModel(application) {


    val detailsLiveData=MutableLiveData<CityListItem>()

    fun getDataFromRoom(uuid:Int) {
        launch {

            val dao=HomicideDatabase(getApplication()).homicideDao()
            val homicide=dao.getHomicide(uuid)
            detailsLiveData.value=homicide
        }

    }

}