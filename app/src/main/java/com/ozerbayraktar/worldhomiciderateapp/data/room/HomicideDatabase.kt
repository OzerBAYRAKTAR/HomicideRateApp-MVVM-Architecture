package com.ozerbayraktar.worldhomiciderateapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem


@Database(entities = arrayOf(CityListItem::class), version = 1)
abstract class HomicideDatabase:RoomDatabase(){

    abstract fun homicideDao(): Dao

    //Singleton mantığı -> tüm scopelardan buraya erişilebilinsin

    //statik obje->her yerden erişilebilir.
    companion object{

        //volatile -> instance objesi farklı threadlere görünür hale getirildi.
        @Volatile private var instance:HomicideDatabase?=null

        private val lock=Any()

        //instance varsa invoke'a eşitle, yoksa eğer synchronize bir şekilde, makeroomdatabase i oluştur ve instance e eşitle
        //synchorinzed->aynı anda tek bir threadde işlem yapılacak.
        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance?: makeRoomDatabase(context).also {
                instance=it
            }

        }

        private fun makeRoomDatabase(context:Context)= Room.databaseBuilder(
            context.applicationContext,HomicideDatabase::class.java,"homicidedatabase"
        ).build()


    }
}