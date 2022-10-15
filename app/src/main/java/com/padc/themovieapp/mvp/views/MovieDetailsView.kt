package com.padc.themovieapp.mvp.views

import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.MovieVO

interface MovieDetailsView: BaseView {
    fun showMovieDetails(movie: MovieVO)
    fun showCreditByMovie(cast: List<ActorVO>, crew: List<ActorVO>)
    fun navigateBack()
    fun navigateToMovieTrailer(key: String)
    fun showFavourite(isFavourite: Boolean)
}