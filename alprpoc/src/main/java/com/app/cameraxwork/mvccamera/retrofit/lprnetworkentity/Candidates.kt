package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Candidates (

  @SerializedName("score" ) @Expose var score : Double? = null,
  @SerializedName("plate" ) @Expose var plate : String? = null

)