package com.padc.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themovieapp.data.vos.GenreVO

data class GetGenreResponse(
    @SerializedName("genres")
    val genre: List<GenreVO>?
)