package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.ProductionCompaniesVO

class ProductionCompanyTypeConverter {

    @TypeConverter
    fun toString(productionCompaniesList: List<ProductionCompaniesVO>?): String{
        return Gson().toJson(productionCompaniesList)
    }

    @TypeConverter
    fun toProductionCompanies(productionCompaniesListJsonString: String): List<ProductionCompaniesVO>?{
        val productionCompaniesListType = object :TypeToken<List<ProductionCompaniesVO>?>(){}.type
        return Gson().fromJson(productionCompaniesListJsonString, productionCompaniesListType)
    }
}