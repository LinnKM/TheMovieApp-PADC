package com.padc.themovieapp.data.models

import androidx.lifecycle.LiveData
import com.padc.themovieapp.data.vos.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : BaseModel(), MovieModel {

    override fun getNowPlayingMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        //Network
        mMovieApi.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(type = NOW_PLAYING)
    }

    override fun getPopularMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        // Network
        mMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = POPULAR }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(type = POPULAR)
    }

    override fun getTopRatedMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        // Network
        mMovieApi.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = TOP_RATED }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(type = TOP_RATED)
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genre ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMoviesByGenre(genreId = genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getActors(onFailure: (String) -> Unit): LiveData<List<ActorVO>>? {
        // Network
        mMovieApi.getActors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMovieDatabase?.actorDao()?.insertActors(it.results)
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.actorDao()?.getAllActors()
    }

    override fun getMovieDetails(
        movieId: String,
        onFailure: (String) -> Unit
    ): LiveData<MovieVO?>? {
        // Network
        mMovieApi.getMovieDetails(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromDatabase =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId = movieId.toInt())

                it.type = movieFromDatabase?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
    }

    override fun getMovieTrailers(
        movieId: String,
        onSuccess: (List<TrailerVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMovieTrailer(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getCreditsByMovie(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun searchMovie(query: String): Observable<List<MovieVO>> {
        return mMovieApi
            .searchMovie(query = query)
            .map { it.results ?: listOf() }
            .onErrorResumeNext { Observable.just(listOf()) }
            .subscribeOn(Schedulers.io())
    }
}