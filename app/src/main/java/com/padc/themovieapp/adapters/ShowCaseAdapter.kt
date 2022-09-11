package com.padc.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themovieapp.R
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.themovieapp.viewholders.ShowCaseViewHolder

class ShowCaseAdapter(private val delegate: ShowCaseViewHolderDelegate) : RecyclerView.Adapter<ShowCaseViewHolder>() {
    private var mMovieList: List<MovieVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_show_case, parent, false)
        return ShowCaseViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: ShowCaseViewHolder, position: Int) {
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