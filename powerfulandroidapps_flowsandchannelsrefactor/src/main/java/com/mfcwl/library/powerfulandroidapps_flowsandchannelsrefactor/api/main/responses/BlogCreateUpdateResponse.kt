package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.main.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.BlogPost
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.DateUtils

class BlogCreateUpdateResponse(

    @SerializedName("response")
    @Expose
    var response: String,

    @SerializedName("pk")
    @Expose
    var pk: Int,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("slug")
    @Expose
    var slug: String,

    @SerializedName("body")
    @Expose
    var body: String,

    @SerializedName("image")
    @Expose
    var image: String,

    @SerializedName("date_updated")
    @Expose
    var date_updated: String,

    @SerializedName("username")
    @Expose
    var username: String


) {
    fun toBlogPost(): BlogPost {
        return BlogPost(
            pk = pk,
            title = title,
            slug = slug,
            body = body,
            image = image,
            date_updated = DateUtils.convertServerStringDateToLong(
                date_updated
            ),
            username = username
        )
    }
}