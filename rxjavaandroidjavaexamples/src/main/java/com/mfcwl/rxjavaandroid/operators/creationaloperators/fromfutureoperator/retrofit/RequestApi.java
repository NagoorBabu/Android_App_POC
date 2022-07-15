package com.mfcwl.rxjavaandroid.operators.creationaloperators.fromfutureoperator.retrofit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("todos/1")
    Observable<ResponseBody> makeObservableQuery();
}
