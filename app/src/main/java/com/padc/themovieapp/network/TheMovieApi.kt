package com.padc.themovieapp.network

import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.network.responses.*
import com.padc.themovieapp.utils.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieApi {

    @GET(API_GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Observable<MovieListResponse>

    @GET(API_GET_POPULAR)
    fun getPopularMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Observable<MovieListResponse>

    @GET(API_GET_TOP_RATED)
    fun getTopRatedMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Observable<MovieListResponse>

    @GET(API_GET_GENRES)
    fun getGenres(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
    ): Observable<GetGenreResponse>

    @GET(API_GET_MOVIE_BY_GENRE)
    fun getMoviesByGenre(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_GENRE_ID) genreId: String
    ): Observable<MovieListResponse>

    @GET(API_GET_ACTORS)
    fun getActors(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY
    ): Observable<ActorListResponse>

    @GET("$API_GET_MOVIE_DETAILS/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY
    ): Observable<MovieVO>

    @GET(API_GET_MOVIE_TRAILER)
    fun getMovieTrailer(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY
    ): Observable<TrailerListResponse>

    @GET("$API_GET_CREDIT_BY_MOVIE/{movie_id}/credits")
    fun getCreditsByMovie(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY
    ): Observable<GetCreditsByMovieResponse>

    @GET(API_SEARCH_MOVIE)
    fun searchMovie(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_QUERY) query: String
    ): Observable<MovieListResponse>
}