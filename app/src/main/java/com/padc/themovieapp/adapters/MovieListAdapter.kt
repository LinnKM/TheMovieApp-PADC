package com.padc.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themovieapp.R
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.themovieapp.viewholders.PopularMovieViewHolder

class MovieListAdapter(private val delegate: MovieViewHolderDelegate): RecyclerView.Adapter<PopularMovieViewHolder>() {
    private var mMovieList: List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_popular_movie_one, parent, false)
        return PopularMovieViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        if(mMovieList.isNotEmpty()){
            holder.bindData(mMovieList[position])
        }
    }

    override fun getItemCount(): Int {
        return mMovieList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieList: List<MovieVO>){
        mMovieList = movieList
        notifyDataSetChanged()
    }
}