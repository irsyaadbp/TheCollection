package com.irsyaad.dicodingsubmission.thecollection.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.adapter.FilmRecyclerAdapter
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.DataViewModel
import com.irsyaad.dicodingsubmission.thecollection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_film.*

class FilmFragment : Fragment() {

    private lateinit var viewModel: DataViewModel
    private lateinit var lang: String
    private lateinit var filmAdapter: FilmRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lang = "en-Us"

        viewModel = ViewModelProviders.of(this, ViewModelFactory().viewModelFactory{ DataViewModel(lang)})[DataViewModel::class.java]
        isLoading()
        isError()
        viewModel.getDataFilm().observe(this, Observer { result ->
            if(result != null){
                filmAdapter.setData(result)
            }else{
                Toast.makeText(context, "Tidak ada data", Toast.LENGTH_LONG).show()
            }
        })

        filmAdapter = FilmRecyclerAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }
    }

    private fun isLoading(){
        viewModel.showLoading.observe(this, Observer {status ->
            if(status){
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }else{
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun isError(){
        viewModel.isError.observe(this, Observer { status ->
            if(status){
                Toast.makeText(context, "Connection Error :(", Toast.LENGTH_LONG).show()
            }
        })
    }
}
