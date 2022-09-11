package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.MovieVO

class MovieTypeConverter {

    @TypeConverter
    fun toString(movieList: List<MovieVO>?): String {
        return Gson().toJson(movieList)
    }

    @TypeConverter
    fun toMovies(movieListJsonString: String): List<MovieVO>? {
        val movieListType = object :TypeToken<List<MovieVO>?>(){}.type
        return Gson().fromJson(movieListJsonString, movieListType)
    }
}