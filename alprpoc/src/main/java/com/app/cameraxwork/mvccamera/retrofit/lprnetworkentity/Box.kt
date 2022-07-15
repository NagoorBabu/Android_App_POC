package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Box(

    @SerializedName("xmin") @Expose var xmin: Int? = null,
    @SerializedName("ymin") @Expose var ymin: Int? = null,
    @SerializedName("xmax") @Expose var xmax: Int? = null,
    @SerializedName("ymax") @Expose var ymax: Int? = null

)