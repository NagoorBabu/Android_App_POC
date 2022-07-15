package com.mfcwl.rxjavaandroid.operators.creationaloperators.frompublisheroperator.retrofit;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("todos/1")
    Flowable<ResponseBody> makeQuery();
}
