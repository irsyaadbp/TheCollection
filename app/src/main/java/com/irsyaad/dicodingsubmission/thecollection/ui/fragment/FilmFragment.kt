package com.irsyaad.dicodingsubmission.thecollection.ui.fragment


import android.os.Bundle
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
import com.irsyaad.dicodingsubmission.thecollection.model.DataModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_film.*

class FilmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lang = "id"

        val viewModel: DataViewModel = ViewModelProviders.of(this, viewModelFactory{ DataViewModel(lang)})[DataViewModel::class.java]

        viewModel.getDataFilm().observe(this, Observer {
            val filmAdapter = DataRecyclerAdapter(it)

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = filmAdapter
            }
        })

    }



    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }


}
