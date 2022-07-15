package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Orientation(

    @SerializedName("orientation") @Expose var orientation: String? = null,
    @SerializedName("score") @Expose var score: Double? = null

)