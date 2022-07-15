package com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Results (

  @SerializedName("box"         ) @Expose var box         : Box?                   = Box(),
  @SerializedName("plate"       ) @Expose var plate       : String?                = null,
  @SerializedName("region"      ) @Expose var region      : Region?                = Region(),
  @SerializedName("vehicle"     ) @Expose var vehicle     : Vehicle?               = Vehicle(),
  @SerializedName("score"       ) @Expose var score       : Double?                = null,
  @SerializedName("candidates"  ) @Expose var candidates  : ArrayList<Candidates>  = arrayListOf(),
  @SerializedName("dscore"      ) @Expose var dscore      : Double?                = null,
  @SerializedName("model_make"  ) @Expose var modelMake   : ArrayList<ModelMake>   = arrayListOf(),
  @SerializedName("color"       ) @Expose var color       : ArrayList<Color>       = arrayListOf(),
  @SerializedName("orientation" ) @Expose var orientation : ArrayList<Orientation> = arrayListOf()

)