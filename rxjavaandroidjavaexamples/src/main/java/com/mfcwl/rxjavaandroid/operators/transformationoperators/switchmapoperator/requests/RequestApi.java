package com.mfcwl.rxjavaandroid.operators.transformationoperators.switchmapoperator.requests;

import com.mfcwl.rxjavaandroid.operators.transformationoperators.switchmapoperator.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {
    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts/{id}")
    Observable<Post> getPost(
            @Path("id") int id
    );
}
