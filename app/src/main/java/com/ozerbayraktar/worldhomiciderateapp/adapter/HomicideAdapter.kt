package com.ozerbayraktar.worldhomiciderateapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ozerbayraktar.worldhomiciderateapp.R
import com.ozerbayraktar.worldhomiciderateapp.model.CityList
import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem
import com.ozerbayraktar.worldhomiciderateapp.presentation.view.FirstFragmentDirections
import kotlinx.android.synthetic.main.item_row.view.*
import javax.inject.Inject

class HomicideAdapter @Inject constructor (
    val homicideList:ArrayList<CityListItem>): RecyclerView.Adapter<HomicideAdapter.HomicideViewHolder>() {


    class HomicideViewHolder(var view:View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomicideViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false)
        return HomicideViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomicideViewHolder, position: Int) {
        holder.view.rankTextAdapter.text=homicideList[position].Rank
        holder.view.cityTextAdapter.text=homicideList[position].City

        holder.view.setOnClickListener {
            val action=FirstFragmentDirections.actionFirstFragmentToDetailFragment(homicideList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return homicideList.size
    }

    //when we swpipe the page, the adapter will get the new homicidelist with this function.
    fun updateHomicideList(newHomicideList:List<CityListItem>){
        homicideList.clear()
        homicideList.addAll(newHomicideList)
        notifyDataSetChanged()
    }
}