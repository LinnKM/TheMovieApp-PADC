package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.GenreVO

class GenreTypeConverter {

    @TypeConverter
    fun toString(genresList: List<GenreVO>?): String{
        return Gson().toJson(genresList)
    }

    @TypeConverter
    fun toGenres(genreListJsonString: String): List<GenreVO>?{
        val genresListType = object :TypeToken<List<GenreVO>?>(){}.type
        return Gson().fromJson(genreListJsonString, genresListType)
    }
}