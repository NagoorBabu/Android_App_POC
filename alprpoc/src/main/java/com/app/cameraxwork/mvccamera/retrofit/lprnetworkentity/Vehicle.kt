package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Vehicle (

  @SerializedName("score" ) @Expose var score : Double? = null,
  @SerializedName("type"  ) @Expose var type  : String? = null,
  @SerializedName("box"   ) @Expose var box   : Box?    = Box()

)