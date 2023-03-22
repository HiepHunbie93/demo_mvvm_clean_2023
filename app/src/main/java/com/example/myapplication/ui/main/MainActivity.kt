package com.example.myapplication.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.model.SearchModel
import com.example.myapplication.ui.datalist.MovieListViewModel
import com.example.myapplication.ui.datalist.list.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var valueRepeat = 3
    private lateinit var movieRecyclerView   : RecyclerView
    private lateinit var movieAdapter        : MovieAdapter
    private lateinit var progressBar        : ProgressBar
    private lateinit var search             : String
    private lateinit var layoutManager : GridLayoutManager
    private lateinit var sort : Button
    private var tempMovieList = arrayListOf<SearchModel>()
    private var page = 1
    private val movieListViewModel : MovieListViewModel by viewModels()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sort = findViewById(R.id.btSort)
        progressBar         = findViewById(R.id.progressBar)
        movieRecyclerView   = findViewById(R.id.rycList)
        layoutManager = GridLayoutManager(this,2)
        recyclerView()
        sort.setOnClickListener{
            tempMovieList.sortWith { o1, o2 -> o1!!.year.compareTo(o2!!.year) }
            movieAdapter.setData(tempMovieList)
        }
        movieRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(layoutManager.findLastVisibleItemPosition()==layoutManager.itemCount-1)
                {
                    page += 1
                    movieListViewModel.getAllData(page.toString())
                    callAPI()
                }
            }
        })
    }
    private fun callAPI(){
        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat){
                movieListViewModel._movieListValue.collect{value->
                    when {
                        value.isLoading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
                            progressBar.visibility = View.GONE
                            valueRepeat = 0
                            Toast.makeText(this@MainActivity, value.error, Toast.LENGTH_LONG).show()
                        }
                        value.moviesList.isNotEmpty() -> {
                            progressBar.visibility = View.GONE
                            valueRepeat = 0
                            tempMovieList.addAll(value.moviesList)
                            movieAdapter.setData(tempMovieList as ArrayList<SearchModel>)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.menuSearch)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(queryText: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.isEmpty() == true){
            movieAdapter.setData(tempMovieList)
        }
        else{
            movieAdapter.filter.filter(newText)
        }

        return true
    }

    private fun recyclerView(){
        movieAdapter = MovieAdapter(this@MainActivity,ArrayList())
        movieRecyclerView.adapter = movieAdapter
        movieRecyclerView.layoutManager = layoutManager
        movieRecyclerView.addItemDecoration(DividerItemDecoration(movieRecyclerView.context,(GridLayoutManager(this,1)).orientation))
    }

    override fun onStart() {
        super.onStart()
        movieListViewModel.getAllData(page.toString())
        callAPI()
    }
}