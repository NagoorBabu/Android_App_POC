package com.mfcwl.rxjavaandroid.operators.transformationoperators.concatmapoperator.requests;

import com.mfcwl.rxjavaandroid.operators.transformationoperators.concatmapoperator.models.Comment;
import com.mfcwl.rxjavaandroid.operators.transformationoperators.concatmapoperator.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {
    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Observable<List<Comment>> getComments(
            @Path("id") int id
    );
}
