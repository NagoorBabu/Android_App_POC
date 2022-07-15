package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class LprNetworkEntity(
    @SerializedName("processing_time") @Expose var processingTime: Double? = null,
    @SerializedName("results") @Expose var results: ArrayList<Results> = arrayListOf(),
    @SerializedName("filename") @Expose var filename: String? = null,
    @SerializedName("version") @Expose var version: Int? = null,
    @SerializedName("camera_id") @Expose var cameraId: String? = null,
    @SerializedName("timestamp") @Expose var timestamp: String? = null

)