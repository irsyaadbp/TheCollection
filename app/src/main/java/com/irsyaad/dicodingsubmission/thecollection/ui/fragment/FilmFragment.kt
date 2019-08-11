package com.irsyaad.dicodingsubmission.thecollection.ui.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.adapter.DataRecyclerAdapter
import com.irsyaad.dicodingsubmission.thecollection.model.Results
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_film.*

@Suppress("UNCHECKED_CAST")
class FilmFragment : Fragment() {

    private lateinit var viewModel: DataViewModel
    private lateinit var lang: String
    private lateinit var filmAdapter: DataRecyclerAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        Log.d("uye", "onAttach")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("uye", "onCreateView")
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("uye", "onActivityCreated")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("uye", "onViewCreated")

        lang = "en-Us"

        filmAdapter = DataRecyclerAdapter()
        filmAdapter.notifyDataSetChanged()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory{ DataViewModel(lang)})[DataViewModel::class.java]

        viewModel.getDataFilm().observe(this, Observer<List<Results>> { result ->

            filmAdapter.setData(result)

            Log.d("uye3", "set data untuk adapter")
        })

        btnGanti.setOnClickListener {
            lang = "id"
            viewModel.setDataFilm(lang)
            Log.d("uye4", "clicked")
        }


    }



    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }


}
