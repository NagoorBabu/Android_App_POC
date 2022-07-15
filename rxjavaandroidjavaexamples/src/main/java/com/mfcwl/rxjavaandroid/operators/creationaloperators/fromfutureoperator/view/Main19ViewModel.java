package com.mfcwl.rxjavaandroid.operators.creationaloperators.fromfutureoperator.view;

import androidx.lifecycle.ViewModel;

import com.mfcwl.rxjavaandroid.operators.creationaloperators.fromfutureoperator.repository.Repository;

import java.util.concurrent.Future;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

public class Main19ViewModel extends ViewModel {

    private Repository repository;

    public Main19ViewModel() {
        repository = Repository.getInstance();
    }

    public Future<Observable<ResponseBody>> makeFutureQuery(){
        return repository.makeFutureQuery();
    }
}