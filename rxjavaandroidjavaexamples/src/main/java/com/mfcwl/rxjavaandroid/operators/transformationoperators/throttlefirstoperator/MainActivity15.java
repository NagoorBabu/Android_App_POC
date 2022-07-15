package com.mfcwl.rxjavaandroid.operators.transformationoperators.throttlefirstoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;
import com.mfcwl.rxjavaandroid.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import kotlin.Unit;

/*The ThrottleFirst() operator filters out items emitted by the source Observable that are within a timespan.*/
public class MainActivity15 extends AppCompatActivity {

    private static final String TAG = "MainActivity15";

    //ui
    private Button button;

    // vars
    private io.reactivex.disposables.CompositeDisposable disposables = new CompositeDisposable();
    private long timeSinceLastRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
        button = findViewById(R.id.button);

        timeSinceLastRequest = System.currentTimeMillis();

        /*This will restrict multiple clicks based on the time specified*/
        // Set a click listener to the button with RxBinding Library
        RxView.clicks(button)
                .throttleFirst(2000, TimeUnit.MILLISECONDS) // Throttle the clicks so 500 ms must pass before registering a new click
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Unit>() {

                    @Override
                    public void onSubscribe(io.reactivex.disposables.Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Unit unit) {
                        Log.d(TAG, "onNext: time since last clicked: " + (System.currentTimeMillis() - timeSinceLastRequest));
                        someMethod(); // Execute some method when a click is registered
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void someMethod() {
        timeSinceLastRequest = System.currentTimeMillis();
        // do something
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // Dispose observable
    }

}