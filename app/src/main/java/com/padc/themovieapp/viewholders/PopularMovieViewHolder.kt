package com.padc.themovieapp.viewholders

import android.graphics.Movie
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.themovieapp.R
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_popular_movie_one.view.*

class PopularMovieViewHolder(itemView: View, private val delegate: MovieViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                delegate.onTapMovie(movie.id)
            }
        }
    }

    fun bindData(movie: MovieVO){
        mMovie = movie
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .placeholder(R.drawable.solid_black_placeholder)
            .into(itemView.ivMovieImage)

        itemView.tvMovieName.text = movie.title
        itemView.tvMovieRating.text = movie.voteAverage?.toString()
        itemView.rbMovieRating.rating = movie.getRatingBasedOnFiveStars()
    }
}