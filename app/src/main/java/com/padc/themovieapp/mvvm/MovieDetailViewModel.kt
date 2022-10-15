package com.padc.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.utils.TYPE_TRAILER
import java.lang.reflect.Type

class MovieDetailViewModel : ViewModel() {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // LiveData
    var movieDetailLiveData: LiveData<MovieVO?>? = null
    var castLiveData = MutableLiveData<List<ActorVO>>()
    var crewLiveData = MutableLiveData<List<ActorVO>>()
    var trailerLiveData = MutableLiveData<String>()
    var mErrorLiveData = MutableLiveData<String>()

    fun getInitialData(movieId: Int) {
        movieDetailLiveData =
            mMovieModel.getMovieDetails(movieId = movieId.toString()) { mErrorLiveData.postValue(it) }

        mMovieModel.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                castLiveData.postValue(it.first?: listOf())
                crewLiveData.postValue(it.second?: listOf())
            }, onFailure = {
                mErrorLiveData.postValue(it)
            }
        )

        mMovieModel.getMovieTrailers(
            movieId = movieId.toString(),
            onSuccess = {
                if(it.isNotEmpty()) {
                    val trailer = it.filter { it.type == TYPE_TRAILER }
                    trailerLiveData.postValue(trailer[0].key?: "")
                }
            }, onFailure = {

            }
        )
    }
}