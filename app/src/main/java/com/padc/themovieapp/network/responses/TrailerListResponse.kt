package com.padc.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themovieapp.data.vos.TrailerVO

data class TrailerListResponse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("results")
    val results: List<TrailerVO>?
)