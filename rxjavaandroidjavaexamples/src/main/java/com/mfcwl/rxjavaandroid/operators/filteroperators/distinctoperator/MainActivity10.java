package com.mfcwl.rxjavaandroid.operators.filteroperators.distinctoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mfcwl.rxjavaandroid.R;
import com.mfcwl.rxjavaandroid.introductiontoobservableandobserver.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*The distinct() operator is another one I bet you'll be using frequently.
 You can "filter" custom objects based on distinct fields.
  I think this is a tricky concept to imagine without an example.
   So if you're confused take a look at the examples below.

The Distinct operator filters an Observable by only allowing items through that have not already been emitted.
But what defines the object as "distinct" is up to the developer to determine.

*/
public class MainActivity10 extends AppCompatActivity {

    private static final String TAG = "MainActivity10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        inCorrectExample();
        correctExample();
    }

    public static List<Task> createTasksList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Take out the trash", true, 3));
        tasks.add(new Task("Walk the dog", false, 2));
        tasks.add(new Task("Make my bed", true, 1));
        tasks.add(new Task("Unload the dishwasher", false, 0));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5)); // duplicate for testing the distinct operator
        return tasks;
    }

    private void inCorrectExample() {
        Observable<Task> taskObservable = Observable
                .fromIterable(createTasksList())
                .distinct(new Function<Task, Task>() {
                    @Override
                    public Task apply(Task task) throws Throwable {
                        return task;  /* No Logic is written, All Tasks will be displayed including duplicates*/
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

    private void correctExample() {

        Observable<Task> taskObservable = Observable
                .fromIterable(createTasksList())
                .distinct(new Function<Task, String>() {
                    @Override
                    public String apply(Task task) throws Throwable {
                        return task.getDescription(); /*Gives only distinct descriptions,
                         Hence duplicate item with same description will not be emitted*/
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