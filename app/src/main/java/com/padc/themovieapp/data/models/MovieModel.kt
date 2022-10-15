package com.padc.themovieapp.data.models

import androidx.lifecycle.LiveData
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.data.vos.TrailerVO
import io.reactivex.rxjava3.core.Observable

interface MovieModel {
    fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getPopularMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getGenres(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getActors(
        onFailure: (String) -> Unit
    ): LiveData<List<ActorVO>>?

    fun getMovieDetails(
        movieId: String,
        onFailure: (String) -> Unit
    ): LiveData<MovieVO?>?

    fun getMovieTrailers(
        movieId: String,
        onSuccess: (List<TrailerVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun searchMovie(query: String): Observable<List<MovieVO>>
}