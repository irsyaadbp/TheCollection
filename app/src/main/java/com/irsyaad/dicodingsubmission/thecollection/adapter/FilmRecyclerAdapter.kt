package com.irsyaad.dicodingsubmission.thecollection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irsyaad.dicodingsubmission.thecollection.R
import com.irsyaad.dicodingsubmission.thecollection.model.ListDetailFilm
import kotlinx.android.synthetic.main.item_list_layout.view.*

class FilmRecyclerAdapter(private val context: Context, val clickListener: (ListDetailFilm) -> Unit) : RecyclerView.Adapter<FilmRecyclerAdapter.DataViewHolder>() {
    private var data: List<ListDetailFilm> = listOf()

    fun setData(mData: List<ListDetailFilm>){
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
        holder.bind(data[position], context)
        holder.itemView.setOnClickListener{clickListener(data[position])}
    }

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle = view.txtTitle
        private val txtDesc = view.txtDescription
        private val txtRating = view.txtRating
        private val imgPoster = view.imgPoster

        fun bind(data: ListDetailFilm, context: Context){

            txtTitle.text = data.title
            txtDesc.text = data.overview
            txtRating.text = "${data.vote_average}"

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185${data.poster_path}")
                    .centerCrop()
                    .into(imgPoster)

        }
    }

}
