package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.ProductionCountriesVO

class ProductionCountryTypeConverter {

    @TypeConverter
    fun toString(productionCountriesList: List<ProductionCountriesVO>?): String{
        return Gson().toJson(productionCountriesList)
    }

    @TypeConverter
    fun toProductionCountries(productionCountriesListJsonString: String): List<ProductionCountriesVO>?{
        val productionCountriesListType = object :TypeToken<List<ProductionCountriesVO>?>(){}.type
        return Gson().fromJson(productionCountriesListJsonString, productionCountriesListType)
    }
}