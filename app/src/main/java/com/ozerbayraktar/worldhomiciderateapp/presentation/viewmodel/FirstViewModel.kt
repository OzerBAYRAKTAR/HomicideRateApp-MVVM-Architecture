package com.ozerbayraktar.worldhomiciderateapp.presentation.viewmodel


import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozerbayraktar.worldhomiciderateapp.data.room.HomicideDatabase
import com.ozerbayraktar.worldhomiciderateapp.data.apiservice.HomicideApiService
import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem
import com.ozerbayraktar.worldhomiciderateapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class FirstViewModel (aplication: Application): BaseViewModel(aplication) {
    private val homicideApiService=HomicideApiService()
    val disposable= CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    private var initalHomicideList= listOf<CityListItem>()
    private var isSearchStarting=true



    val homicides =MutableLiveData<List<CityListItem>>()
    val homicidesError = MutableLiveData<Boolean>()
    val homicidesLoading = MutableLiveData<Boolean>()


    fun refreshData(context: Context) {
        val updateTime = customSharedPreferences.getTime()
        if (updateTime != null && (updateTime != 0L) && (System.nanoTime() - updateTime < refreshTime)) {

            getDataFromRoom()

        } else {
            getDataFromApi(context)

        }

    }

    fun SearchList(query:String) {
        val listToSearch = if (isSearchStarting) {
            homicides.value
        } else {
            initalHomicideList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                homicides.postValue(initalHomicideList)
                isSearchStarting=true
                return@launch
            }
            //arama yerine bir şeyler yazılmışsa
            listToSearch?.let {
                val result=listToSearch.filter {
                    it.City.contains(query.trim(),ignoreCase = true)
                }
            //ilk defa arama yapılacaksa
                if (isSearchStarting) {
                    initalHomicideList= homicides.value!!
                    isSearchStarting=false
            }
                homicides.postValue(result)
            }


        }

    }

    fun refreshFromApi(context: Context) {
        getDataFromApi(context)
    }

    private fun getDataFromRoom() {
        homicidesLoading.value=true
        launch {
            val homicides=HomicideDatabase(getApplication()).homicideDao().getAllHomicides()
            showHomicides(homicides)

        }
    }

    private fun getDataFromApi(context: Context) {
        homicidesLoading.value=true

        disposable.add(
            homicideApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CityListItem>>() {
                    override fun onSuccess(t: List<CityListItem>) {

                        storeInRoom(t,context)   // veriler indirildiğinde sql'e kaydediliyor

                    }

                    override fun onError(e: Throwable) {
                        homicidesLoading.value=false
                        homicidesError.value=true
                        e.printStackTrace()
                    }

                })
        )
    }

     private fun showHomicides(homicideList:List<CityListItem>) {
         homicides.value=homicideList
         homicidesError.value=false
         homicidesLoading.value=false
     }

    private fun storeInRoom(list: List<CityListItem>,context: Context) {
         viewModelScope.launch(Dispatchers.Main) {

             val dao=HomicideDatabase(context).homicideDao()
             dao.deleteAllHomicides()

             val listLong=dao.insertAll(*list.toTypedArray())    // list to -> individual parameters
             var i=0
             while(i<list.size){
                 list[i].uuid=listLong[i].toInt()
                 i++
             }
             showHomicides(list)

         }
         customSharedPreferences.saveTime(System.nanoTime())
     }
}