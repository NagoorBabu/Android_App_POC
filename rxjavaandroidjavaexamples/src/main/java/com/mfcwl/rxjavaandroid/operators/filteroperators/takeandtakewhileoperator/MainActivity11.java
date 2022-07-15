package com.mfcwl.rxjavaandroid.operators.filteroperators.takeandtakewhileoperator;

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

/*take() and takeWhile() fall into the filters category.
 They are similar to the filter() operator in that they filter through lists of objects.

The main difference between the take() operators and the filter() operator is that the filter() operator will check every object in the list.
 So you could say the filter() operator is inclusive.

Whereas the take() operators would be considered exclusive because they don't necessary check every item in the list.
 They will emit objects only until the condition of their function is satisfied.*/
public class MainActivity11 extends AppCompatActivity {

    private static final String TAG = "MainActivity11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        /*Take Operator is used in conjunction with list operators,
         * Using take operator we can mention no. of items to be emitted*/
        takeExample();
        takeWhileExample();

    }

    private void takeExample() {
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .take(3)   /*This will only print first 3 items*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void takeWhileExample() {

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .takeWhile(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.isComplete();   /*From First Item, This condition is checked,
                        If First Item check is failed then, No Items will be emitted, Otherwise,
                        It will emit Items until this check is failed.*/
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
                Log.d(TAG, "onNext: " + task.getDescription());
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