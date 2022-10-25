package com.ozerbayraktar.worldhomiciderateapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.ozerbayraktar.worldhomiciderateapp.R
import com.ozerbayraktar.worldhomiciderateapp.model.CityListItem
import com.ozerbayraktar.worldhomiciderateapp.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private var homicideUuid=0
    private lateinit var viewModel:DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            homicideUuid=DetailFragmentArgs.fromBundle(it).homicideUuid
        }

        viewModel=ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getDataFromRoom(homicideUuid)


        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { homicide ->
            homicide?.let {
                detRank.text="Rank : "+homicide.Rank
                detCity.text="City : "+homicide.City
                detCountry.text="Country : "+homicide.Country
                detHomicide.text="Homicide : "+homicide.Homicides
                detPopulation.text="Population : "+homicide.Population
                detPerHomicide.text="Homicide Per 100.000 : "+homicide.Homicidesper
            }

        })

    }


}