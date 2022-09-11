package com.padc.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.delegates.BannerViewHolderDelegate
import com.padc.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.view_item_banner_1.view.*

class BannerViewHolder(itemView: View, private val delegate: BannerViewHolderDelegate): RecyclerView.ViewHolder(itemView) {
    private var mMovie: MovieVO? = null

    init{
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                delegate.onTapMovieFromBanner(movie.id)
            }
        }
    }

    fun bindData(movie: MovieVO){
        mMovie = movie
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivBannerImage1)

        itemView.tvBannerMovieName.text = movie.title
    }
}