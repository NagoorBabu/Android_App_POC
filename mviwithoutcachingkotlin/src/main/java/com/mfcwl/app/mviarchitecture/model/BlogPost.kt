package com.mfcwl.app.mviarchitecture.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogPost(

    @Expose
    @SerializedName("pk")
    val pk: Int? = null,

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("body")
    val body: String? = null,

    @Expose
    @SerializedName("image")
    val image: String? = null
){
    override fun toString(): String {
        return "BlogPost(pk=$pk, title=$title, body=$body, image=$image)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BlogPost

        if (pk != other.pk) return false
        if (title != other.title) return false
        if (body != other.body) return false
        if (image != other.image) return false

        return true
    }


}
