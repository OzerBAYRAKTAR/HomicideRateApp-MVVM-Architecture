package com.ozerbayraktar.worldhomiciderateapp.presentation.view


import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozerbayraktar.worldhomiciderateapp.R
import com.ozerbayraktar.worldhomiciderateapp.adapter.HomicideAdapter
import com.ozerbayraktar.worldhomiciderateapp.presentation.viewmodel.FirstViewModel
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {
    private lateinit var viewModel:FirstViewModel
    private val homicideAdapter=HomicideAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(FirstViewModel::class.java)
        context?.let {
            viewModel.refreshData(it) }

        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=homicideAdapter


        observeLiveData()
        refreshSwipeLayout()
        searchView()

    }

    fun observeLiveData() {
        viewModel.homicides.observe(viewLifecycleOwner, Observer { homicides ->

            homicides?.let {
                recyclerView.visibility=View.VISIBLE
                homicideAdapter.updateHomicideList(homicides)
            }

        })
        viewModel.homicidesError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it) {
                    textError.visibility = View.VISIBLE
                    homicidesProgressBar.visibility = View.GONE
                } else {
                    textError.visibility=View.GONE
                    homicidesProgressBar.visibility=View.GONE
                }
            }

        })
        viewModel.homicidesLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    homicidesProgressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    textError.visibility = View.GONE
                } else {
                    homicidesProgressBar.visibility=View.GONE
                }
            }
        })
    }
    fun refreshSwipeLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility=View.GONE
            textError.visibility=View.GONE
            homicidesProgressBar.visibility=View.VISIBLE
            viewModel.refreshFromApi(context!!)
            swipeRefreshLayout.isRefreshing=false

        }
    }

    fun searchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.SearchList(p0)
                }
                return false
            }

        })

    }
}