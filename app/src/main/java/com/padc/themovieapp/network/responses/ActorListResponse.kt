package com.padc.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themovieapp.data.vos.ActorVO

data class ActorListResponse(

    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<ActorVO>
)