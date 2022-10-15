package com.padc.themovieapp.delegates

interface BannerViewHolderDelegate {
    fun onTapMovieFromBanner(movieId: Int)
    fun onTapTrailerFromBanner(movieId: Int)
}