package com.irsyaad.dicodingsubmission.thecollection.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.Results
import kotlinx.android.synthetic.main.item_list_layout.view.*

class DataRecyclerAdapter : RecyclerView.Adapter<DataViewHolder>() {
    private var data: List<Results> = listOf()
    fun setData(mData: List<Results>){
        data = mData
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        Log.d("uyi", "${data[position]}")
        holder.bind(data[position])
    }

}

class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val txtTitle = view.txtTitle
    private val txtDesc = view.txtDescription
    fun bind(data: Results){
        txtTitle.text = data.title
        txtDesc.text = data.overview
    }
}
