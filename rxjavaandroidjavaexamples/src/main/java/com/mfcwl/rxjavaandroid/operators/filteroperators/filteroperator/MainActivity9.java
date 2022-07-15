package com.mfcwl.rxjavaandroid.operators.filteroperators.filteroperator;

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
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*How many times have you run into a situation when you have a list of custom objects and want to filter based on a specific field? AND the list is very large so the filtering should probably be done on a background thread.

If that happens to you frequently, the filter() operator will become your new best friend.

"The Filter operator filters an Observable by only allowing items through that pass a test that you specify in the form of a predicate function."*/
public class MainActivity9 extends AppCompatActivity {

    private static final String TAG = "MainActivity9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        exampleOne();
        exampleTwo();
    }

    private void exampleOne() {

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.getDescription().equals("Walk the dog"); /*This is done in Background Thread*/
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: This task matches the description: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void exampleTwo() {

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.isComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: This is a completed task: " + task.getDescription());
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