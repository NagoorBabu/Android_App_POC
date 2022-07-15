package com.mfcwl.rxjavaandroid.operators.creationaloperators.frompublisheroperator.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mfcwl.rxjavaandroid.operators.creationaloperators.frompublisheroperator.repository.Repository;

import okhttp3.ResponseBody;

public class Main8ViewModel extends ViewModel {

    private Repository repository;

    public Main8ViewModel() {
        repository = Repository.getInstance();
    }

    public LiveData<ResponseBody> makeQuery() {
        return repository.makeReactiveQuery();
    }
}
