package com.example.myapplication.ui.datalist.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.model.SearchModel
import com.squareup.picasso.Picasso

class MovieAdapter (private val context: Context, var moviesList : ArrayList<SearchModel>)
    : RecyclerView.Adapter<MovieAdapter.SearchViewHolder>(), Filterable {
    lateinit var filteredList : ArrayList<SearchModel>
    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieLayout: LinearLayout = view.findViewById(R.id.movieLinearLayout)
        val movieImage: ImageView = view.findViewById(R.id.imgSearchImage)
        val movieName: TextView = view.findViewById(R.id.txtSearchName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val movieView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return SearchViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val list = moviesList[position]
        holder.movieName.text = list.title
        Picasso.get().load(list.poster).into(holder.movieImage)
        holder.movieLayout.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<SearchModel>) {
        this.filteredList = list
        this.moviesList = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val string = constraint?.toString() ?: ""
                if (string.isNotEmpty()) {
                    Log.d("moviesFirst",moviesList.size.toString())
                    Log.d("moviesSecond",filteredList.size.toString())
                    var arrayList = arrayListOf<SearchModel>()
                    filteredList.filter {
                        it.title?.lowercase()?.contains(string.lowercase()) == true
                    }.forEach{
                        arrayList.add(it)
                    }
                    filteredList.clear()
                    filteredList.addAll(arrayList)
                }
                else{
                    filteredList = moviesList
                }
                return FilterResults().apply {
                    this.values = filteredList
                }
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                (if (results?.values == null)
                    ArrayList<SearchModel>()
                else {
                    setData(filteredList)
                })
            }
        }
    }
}