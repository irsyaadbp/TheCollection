package com.irsyaad.dicodingsubmission.thecollection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.DetailTv
import kotlinx.android.synthetic.main.item_list_layout.view.*

class TvRecyclerAdapter : RecyclerView.Adapter<TvRecyclerAdapter.DataViewHolder>() {
    private var data: List<DetailTv> = listOf()
    fun setData(mData: List<DetailTv>){
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

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle = view.txtTitle
        private val txtDesc = view.txtDescription
        fun bind(data: DetailTv){
            txtTitle.text = data.name
            txtDesc.text = data.overview
        }
    }

}
