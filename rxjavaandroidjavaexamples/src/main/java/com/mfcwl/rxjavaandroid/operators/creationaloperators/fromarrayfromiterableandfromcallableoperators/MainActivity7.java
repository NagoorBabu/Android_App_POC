package com.mfcwl.rxjavaandroid.operators.creationaloperators.fromarrayfromiterableandfromcallableoperators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mfcwl.rxjavaandroid.R;
import com.mfcwl.rxjavaandroid.introductiontoobservableandobserver.DataSource;
import com.mfcwl.rxjavaandroid.introductiontoobservableandobserver.Task;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*fromArray()

This will take an array of objects as input and output an Observable.

It will not execute the method immediately. It will only execute the method once a subscriber has subscribed.

When should you use this operator?
To emit an arbitrary number of items that are known upfront.

Input: T[]
Ouput: Observable<T>*/

/*fromIterable()

This will take an iterable of objects as input and output an Observable. Types of iterables include: List, ArrayList, Set, etc...

It will not execute the method immediately. It will only execute the method once a subscriber has subscribed.

When should you use this operator?
To emit an arbitrary number of items that are known upfront. Same as the fromArray() operator but it's an iterable.

Input: List<T>, ArrayList<T>, Set<T>, etc...
Ouput: Observable<T>*/

/*fromCallable()

fromCallable() will execute a block of code (usually a method) and return a result.

It will not execute the method immediately. It will only execute the method once a subscriber has subscribed.

When should you use this operator?
To generate a single Obseravle item on demand. Like calling a method to retrieve some objects or a list of objects.

Input: Callable<T>
Ouput: T*/


public class MainActivity7 extends AppCompatActivity {

    private static final String TAG = "MainActivity7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        fromArrayExample();
        /*Any kind of Iterable List can be used.*/
        fromIterableExample();
        /*This is used for database transactions.
        Where we want to run our query in the background or get data in main thread*/
        fromCallableExample();
    }

    private void fromCallableExample() {

        Observable<Task> taskObservable = Observable
                .fromCallable(new Callable<Task>() {
                    @Override
                    public Task call() throws Exception {
                        /*This can't be run as we don't have DB setup*/
                        /*return MyDatabase.getTask();*/ /*Database transaction example is to get a task,
                        This can also be a list of tasks based on the data we want*/
                        return null;

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
                Log.d(TAG, "onNext: : " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void fromIterableExample() {

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: : " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void fromArrayExample() {
        Task[] list = new Task[5];
        list[0] = (new Task("Take out the trash", true, 3));
        list[1] = (new Task("Walk the dog", false, 2));
        list[2] = (new Task("Make my bed", true, 1));
        list[3] = (new Task("Unload the dishwasher", false, 0));
        list[4] = (new Task("Make dinner", true, 5));

        Observable<Task> taskObservable = Observable
                .fromArray(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: : " + task.getDescription());
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