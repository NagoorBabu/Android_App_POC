package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog.state

import android.net.Uri
import android.os.Parcelable
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.models.BlogPost
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.BlogQueryUtils.Companion.BLOG_ORDER_ASC
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.BlogQueryUtils.Companion.ORDER_BY_ASC_DATE_UPDATED
import kotlinx.android.parcel.Parcelize

const val BLOG_VIEW_STATE_BUNDLE_KEY =
    "com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog.state.BlogViewState"

@Parcelize
data class BlogViewState(

    // BlogFragment vars
    var blogFields: BlogFields = BlogFields(),

    // ViewBlogFragment vars
    var viewBlogFields: ViewBlogFields = ViewBlogFields(),

// UpdateBlogFragment vars
    var updatedBlogFields: UpdatedBlogFields = UpdatedBlogFields()

) : Parcelable {
    @Parcelize
    data class BlogFields(
        var blogList: List<BlogPost> = ArrayList<BlogPost>(),
        var searchQuery: String = "",
        var page: Int = 1,
        var isQueryInProgress: Boolean = false,
        var isQueryExhausted: Boolean = false,
        var filter: String = ORDER_BY_ASC_DATE_UPDATED,
        var order: String = BLOG_ORDER_ASC,
        var layoutManagerState: Parcelable? = null
    ) : Parcelable

    @Parcelize
    data class ViewBlogFields(
        var blogPost: BlogPost? = null,
        var isAuthorOfBlogPost: Boolean = false
    ) : Parcelable

    @Parcelize
    data class UpdatedBlogFields(
        var updatedBlogTitle: String? = null,
        var updatedBlogBody: String? = null,
        var updatedImageUri: Uri? = null
    ) : Parcelable

}