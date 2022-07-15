package com.mfcwl.app.mviarchitecture.ui.main.state

import com.mfcwl.app.mviarchitecture.model.BlogPost
import com.mfcwl.app.mviarchitecture.model.User

data class MainViewState(

    var blogposts: List<BlogPost>? = null,

    var user: User? = null
)
