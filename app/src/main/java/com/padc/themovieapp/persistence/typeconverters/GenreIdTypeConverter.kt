package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdTypeConverter {

    @TypeConverter
    fun toString(genreIdList: List<Int>?): String{
        return Gson().toJson(genreIdList)
    }

    @TypeConverter
    fun toGenreIds(genreIdListJsonString: String): List<Int>?{
        val genreIdsListType = object :TypeToken<List<Int>?>(){}.type
        return Gson().fromJson(genreIdListJsonString, genreIdsListType)
    }
}