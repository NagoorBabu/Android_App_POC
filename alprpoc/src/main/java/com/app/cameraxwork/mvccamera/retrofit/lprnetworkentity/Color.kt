package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Color(

    @SerializedName("color") @Expose var color: String? = null,
    @SerializedName("score") @Expose var score: Double? = null

)