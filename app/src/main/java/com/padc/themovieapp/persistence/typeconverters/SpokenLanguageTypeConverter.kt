package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.SpokenLanguageVO

class SpokenLanguageTypeConverter {

    @TypeConverter
    fun toString(spokenLanguagesList: List<SpokenLanguageVO>?): String{
        return Gson().toJson(spokenLanguagesList)
    }

    @TypeConverter
    fun toSpokenLanguages(spokenLanguagesListJsonString: String): List<SpokenLanguageVO>?{
        val spokenLanguagesListType = object :TypeToken<List<SpokenLanguageVO>?>(){}.type
        return Gson().fromJson(spokenLanguagesListJsonString, spokenLanguagesListType)
    }
}