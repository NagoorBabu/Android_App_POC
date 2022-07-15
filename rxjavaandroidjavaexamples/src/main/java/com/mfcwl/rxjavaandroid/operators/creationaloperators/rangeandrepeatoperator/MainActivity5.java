package com.mfcwl.rxjavaandroid.operators.creationaloperators.rangeandrepeatoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mfcwl.rxjavaandroid.R;
import com.mfcwl.rxjavaandroid.introductiontoobservableandobserver.Task;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity5 extends AppCompatActivity {

    private static final String TAG = "MainActivity5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        rangeOperatorExample();
        rangeOperatorWithBackgroundWorkExample();
        rangeOperatorAndRepeatOperatorExample();

    }

    private void rangeOperatorAndRepeatOperatorExample() {
        Observable<Integer> integerObservable = Observable
                .range(0, 3) /*Starts from 0 to 3, Here 3 is exclusive hence , 3 will not be printed*/
                .subscribeOn(Schedulers.io())
                .repeat(3) /*Above range will be printed 3 times.*/
                .observeOn(AndroidSchedulers.mainThread());

        integerObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rangeOperatorWithBackgroundWorkExample() {
         /*Range can be used for loops, Where items in the loop may need to perform extension operations.
         Here this use case is done using below example*/
        Observable<Task> taskObservable = Observable
                .range(0, 9)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Task>() {
                    @Override
                    public Task apply(Integer integer) throws Throwable {
                        Log.d(TAG, "apply: " + Thread.currentThread().getName());
                        return new Task("This is a task with priority: " + String.valueOf(integer),
                                false,
                                integer);
                    }
                })
                .takeWhile(new Predicate<Task>() { /*This takeWhile may not be practical as range here will only have values from 0 to 9*/
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.getPriority() < 9;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getPriority());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rangeOperatorExample() {
        /*This example might not be practical*/
        Observable<Integer> integerObservable = Observable
                .range(0, 9) /*Starts from 0 to 9, Here 9 is exclusive hence , 9 will not be printed*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        integerObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
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