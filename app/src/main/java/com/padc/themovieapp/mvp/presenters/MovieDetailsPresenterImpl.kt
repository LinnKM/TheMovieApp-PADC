package com.padc.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.TrailerVO
import com.padc.themovieapp.mvp.views.MovieDetailsView
import com.padc.themovieapp.utils.TYPE_TRAILER

class MovieDetailsPresenterImpl: ViewModel(), MovieDetailsPresenter {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // View
    private var mView: MovieDetailsView? = null

    // States
    private var mTrailers: List<TrailerVO> = listOf()
    private var isFavourite: Boolean = false

    override fun initView(view: MovieDetailsView) {
        mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {
        mMovieModel.getMovieDetails(
            movieId = movieId.toString(),
            onFailure = {
                mView?.showError(it)
            }
        )?.observe(owner) {
            it?.let { mView?.showMovieDetails(it) }
        }

        mMovieModel.getMovieTrailers(
            movieId = movieId.toString(),
            onSuccess = {
                if(it.isNotEmpty()){
                    mTrailers = it.filter { it.type == TYPE_TRAILER }
                }
            }, onFailure = {
                mView?.showError(it)
            }
        )

        mMovieModel.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                mView?.showCreditByMovie(cast = it.first, crew = it.second)
            }, onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }

    override fun onTapTrailer() {
        mView?.navigateToMovieTrailer(mTrailers.firstOrNull()?.key?: "")
    }

    override fun onTapFavourite() {
        when(isFavourite){
            false -> {
                mView?.showFavourite(false)
                isFavourite = true
            }
            else -> {
                mView?.showFavourite(true)
                isFavourite = false
            }

        }
    }

    override fun onUiReady(owner: LifecycleOwner) {}
}