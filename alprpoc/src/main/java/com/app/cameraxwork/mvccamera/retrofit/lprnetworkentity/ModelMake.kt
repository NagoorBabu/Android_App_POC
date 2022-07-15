package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ModelMake(

    @SerializedName("make") @Expose var make: String? = null,
    @SerializedName("model") @Expose var model: String? = null,
    @SerializedName("score") @Expose var score: Double? = null

)