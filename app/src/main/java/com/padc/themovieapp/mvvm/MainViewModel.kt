package com.padc.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.utils.TYPE_TRAILER

class MainViewModel : ViewModel() {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // LiveData
    var nowPlayingMovieLiveData: LiveData<List<MovieVO>>? = null
    var popularMovieLiveData: LiveData<List<MovieVO>>? = null
    var topRatedMovieLiveData: LiveData<List<MovieVO>>? = null
    var genresLiveData = MutableLiveData<List<GenreVO>>()
    var moviesByGenreLiveData = MutableLiveData<List<MovieVO>>()
    var trailerLiveData = MutableLiveData<String>()
    var actorListLiveData: LiveData<List<ActorVO>>? = null
    var mErrorLiveData = MutableLiveData<String>()

    fun getInitialData() {
        nowPlayingMovieLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }
        popularMovieLiveData = mMovieModel.getPopularMovies { mErrorLiveData.postValue(it) }
        topRatedMovieLiveData = mMovieModel.getTopRatedMovies { mErrorLiveData.postValue(it) }
        actorListLiveData = mMovieModel.getActors { mErrorLiveData.postValue(it) }

        mMovieModel.getGenres(
            onSuccess = {
                genresLiveData.postValue(it)
                getMovieByGenre(0)
            }, onFailure = {
                mErrorLiveData.postValue(it)
            }
        )

    }

    fun getMovieByGenre(position: Int) {
        genresLiveData.value?.getOrNull(position)?.id?.let { genreId ->
            mMovieModel.getMoviesByGenre(
                genreId = genreId.toString(),
                onSuccess = {
                    moviesByGenreLiveData.postValue(it)
                }, onFailure = {
                    mErrorLiveData.postValue(it)
                }
            )
        }
    }

    fun getMovieTrailer(movieId: Int) {
        mMovieModel.getMovieTrailers(
            movieId = movieId.toString(),
            onSuccess = {
                if(it.isNotEmpty()) {
                    val trailer = it.filter { it.type == TYPE_TRAILER }
                    trailerLiveData.postValue(trailer[0].key?: "")
                }
            }, onFailure = {
                mErrorLiveData.postValue(it)
            }
        )
    }
}