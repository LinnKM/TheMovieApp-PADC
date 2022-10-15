package com.padc.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.TrailerVO
import com.padc.themovieapp.mvp.views.MainView
import com.padc.themovieapp.utils.TYPE_TRAILER

class MainPresenterImpl : ViewModel(), MainPresenter {

    // View
    var mView: MainView? = null

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // States
    private var mGenres: List<GenreVO> = listOf()
    private var mTrailers: List<TrailerVO> = listOf()

    override fun initView(view: MainView) {
        mView = view
    }

    override fun onUiReady(owner: LifecycleOwner) {
        // Now Playing Movies
        mMovieModel.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }

        // Popular Movies
        mMovieModel.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        // Top Rated Movies
        mMovieModel.getTopRatedMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showTopRateMovies(it)
        }

        // Genre and Get Movies For First Genre
        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it
                mView?.showGenres(it)
                it.firstOrNull()?.id?.let { firstGenreId ->
                    onTapGenre(firstGenreId)
                }
            }, onFailure = {
                mView?.showError(it)
            }
        )

        // Actors
        mMovieModel.getActors {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showActors(it)
        }
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovie(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapTrailerFromBanner(movieId: Int) {
        mMovieModel.getMovieTrailers(
            movieId = movieId.toString(),
            onSuccess = {
                if(it.isNotEmpty()) {
                    mTrailers = it.filter { it.type == TYPE_TRAILER }
                    mView?.navigateToMovieTrailer(mTrailers[0].key?: "")
                }
            }, onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapTrailerFromShowCase(movieId: Int) {
        mMovieModel.getMovieTrailers(
            movieId = movieId.toString(),
            onSuccess = {
                if(it.isNotEmpty()) {
                    mTrailers = it.filter { it.type == TYPE_TRAILER }
                    mView?.navigateToMovieTrailer(mTrailers[0].key?: "")
                }
            }, onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGenre(genrePosition: Int) {
        mGenres.getOrNull(genrePosition)?.id?.let { genreId ->
            mMovieModel.getMoviesByGenre(
                genreId = genreId.toString(),
                onSuccess = {
                    mView?.showMoviesByGenre(it)
                }, onFailure = {
                    mView?.showError(it)
                }
            )
        }
    }

    override fun onTapSearch() {
        mView?.navigateToMovieSearchScreen()
    }
}