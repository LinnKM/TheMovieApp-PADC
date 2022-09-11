package com.padc.themovieapp.network.dataagents

import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.data.vos.TrailerVO
import com.padc.themovieapp.network.TheMovieApi
import com.padc.themovieapp.network.responses.*
import com.padc.themovieapp.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDataAgentImpl : MovieDataAgent {
    private var mTheMovieApi: TheMovieApi? = null

    init {
        val mClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheMovieApi = retrofit.create(TheMovieApi::class.java)
    }

    // Call NowPlayingMovies
    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getNowPlayingMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

    // Call PopularMovies
    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieApi?.getPopularMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val moviesList = response.body()?.results ?: listOf()
                    onSuccess(moviesList)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

    // Call TopRatedMovies
    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getTopRatedMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

    // Call Genres
    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieApi?.getGenres()?.enqueue(object : Callback<GetGenreResponse> {
            override fun onResponse(
                call: Call<GetGenreResponse>,
                response: Response<GetGenreResponse>
            ) {
                if (response.isSuccessful) {
                    val genres = response.body()?.genre ?: listOf()
                    onSuccess(genres)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetGenreResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

    // Call Movies By Genres
    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getMoviesByGenre(genreId = genreId)
            ?.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful) {
                        val movieList = response.body()?.results ?: listOf()
                        onSuccess(movieList)
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    // Call Actors
    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieApi?.getActors()?.enqueue(object : Callback<ActorListResponse>{
            override fun onResponse(
                call: Call<ActorListResponse>,
                response: Response<ActorListResponse>
            ) {
                if(response.isSuccessful){
                    val actorList = response.body()?.results ?: listOf()
                    onSuccess(actorList)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<ActorListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

    // Call Movie Details
    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getMovieDetails(movieId = movieId)?.enqueue(object : Callback<MovieVO>{
            override fun onResponse(call: Call<MovieVO>, response: Response<MovieVO>) {
                if(response.isSuccessful){
                    response.body()?.let{ movie ->
                        onSuccess(movie)
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<MovieVO>, t: Throwable) {
                onFailure(t.message?: "")
            }
        })
    }

    // Call Movie Trailers
    override fun getMovieTrailers(
        movieId: String,
        onSuccess: (List<TrailerVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getMovieTrailer(movieId = movieId)?.enqueue(object: Callback<TrailerListResponse>{
            override fun onResponse(
                call: Call<TrailerListResponse>,
                response: Response<TrailerListResponse>
            ) {
                if(response.isSuccessful){
                    val trailerList = response.body()?.results?: listOf()
                    onSuccess(trailerList)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<TrailerListResponse>, t: Throwable) {
                onFailure(t.message?: "")
            }

        })
    }

    // Call Movie Credits
    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getCreditsByMovie(movieId = movieId)?.enqueue(object : Callback<GetCreditsByMovieResponse>{
            override fun onResponse(
                call: Call<GetCreditsByMovieResponse>,
                response: Response<GetCreditsByMovieResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        onSuccess(Pair(it.cast?: listOf(), it.crew?: listOf()))
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetCreditsByMovieResponse>, t: Throwable) {
                onFailure(t.message?: "")
            }

        })
    }
}