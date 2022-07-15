package com.mfcwl.rxjavaandroid.operators.creationaloperators.frompublisheroperator.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.mfcwl.rxjavaandroid.operators.creationaloperators.frompublisheroperator.retrofit.ServiceGenerator;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Repository {
    private static Repository instance;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }


    public LiveData<ResponseBody> makeReactiveQuery() {
        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getRequestApi()
                .makeQuery()
                .subscribeOn(Schedulers.io()));
    }
}
