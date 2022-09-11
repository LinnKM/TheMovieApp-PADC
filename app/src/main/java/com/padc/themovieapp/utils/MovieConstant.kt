package com.padc.themovieapp.utils

const val MOVIE_API_KEY = "46f8a8d6a7a95ae4c69d3078183fee84"
const val BASE_URL = "https://api.themoviedb.org"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w400/"

// EndPoints
const val API_GET_NOW_PLAYING = "/3/movie/now_playing"
const val API_GET_POPULAR = "/3/movie/popular"
const val API_GET_TOP_RATED = "/3/movie/top_rated"
const val API_GET_GENRES = "/3/genre/movie/list"
const val API_GET_MOVIE_BY_GENRE = "/3/discover/movie"
const val API_GET_ACTORS = "/3/person/popular"
const val API_GET_MOVIE_DETAILS = "/3/movie"
const val API_GET_MOVIE_TRAILER = "/3/movie/{movie_id}/videos"
const val API_GET_CREDIT_BY_MOVIE ="/3/movie"

// Params
const val PARAM_API_KEY = "api_key"
const val PARAM_PAGE = "page"
const val PARAM_GENRE_ID = "with_genres"