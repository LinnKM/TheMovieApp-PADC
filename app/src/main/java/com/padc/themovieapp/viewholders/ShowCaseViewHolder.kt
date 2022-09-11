package com.padc.themovieapp.viewholders

import android.graphics.Movie
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_show_case.view.*

class ShowCaseViewHolder(itemView: View, private val delegate: ShowCaseViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                delegate.onTapMovieFromShowCase(movie.id)
            }
        }
    }

    fun bindData(movie: MovieVO){
        mMovie = movie
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivShowCaseMovieImage)

        itemView.tvShowCaseMovieName.text = movie.title
        itemView.tvShowCaseMovieDate.text = movie.getReleaseDateInDMY()
    }
}