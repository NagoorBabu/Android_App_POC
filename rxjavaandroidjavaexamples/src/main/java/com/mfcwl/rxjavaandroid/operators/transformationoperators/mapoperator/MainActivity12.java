package com.mfcwl.rxjavaandroid.operators.transformationoperators.mapoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mfcwl.rxjavaandroid.R;
import com.mfcwl.rxjavaandroid.introductiontoobservableandobserver.DataSource;
import com.mfcwl.rxjavaandroid.introductiontoobservableandobserver.Task;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*Applies a function to each emitted item. It transforms each emitted item by applying a function to it.*/
public class MainActivity12 extends AppCompatActivity {

    private static final String TAG = "MainActivity12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        convertObjecttoStringExample();
        updateObjectExample();
    }

    private void convertObjecttoStringExample() {

        Observable<String> extractDescription = Observable
                .fromIterable(DataSource.createTasksList())
                .map(new Function<Task, String>() {
                    @Override
                    public String apply(Task task) throws Throwable {
                        Log.d(TAG, "apply: doing work on thread: " + Thread.currentThread().getName());
                        return task.getDescription();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        extractDescription.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: extracted description: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void updateObjectExample() {

        Observable<Task> completeTaskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .map(new Function<Task, Task>() {
                    @Override
                    public Task apply(Task task) throws Throwable {
                        Log.d(TAG, "apply: doing work on thread: " + Thread.currentThread().getName());
                        task.setComplete(true);
                        return task;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        completeTaskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: is this task complete? " + task.isComplete());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}