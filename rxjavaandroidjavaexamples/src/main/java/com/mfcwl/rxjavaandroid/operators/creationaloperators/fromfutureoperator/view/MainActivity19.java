package com.mfcwl.rxjavaandroid.operators.creationaloperators.fromfutureoperator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.mfcwl.rxjavaandroid.R;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*Before I talk about what this method does, let me briefly explain what a Future is.

A future is essentially a pending task. It's a promise for a result from a task when it runs sometime in the future. The task can be a executed via a Runnable or a Callable (not to be confused with an Rx Callable). An example of something that can execute these runnables or callables is an ExecutorService.

So essentially you would use this if you wanted to use an Executor in conjunction with RxJava.


Input: Future<T>
Ouput: Observable<T>*/
public class MainActivity19 extends AppCompatActivity {

    private static final String TAG = "MainActivity19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main19);
        Main19ViewModel viewModel = ViewModelProviders.of(this).get(Main19ViewModel.class);
        try {
            viewModel.makeFutureQuery().get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG, "onSubscribe: called.");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.d(TAG, "onNext: got the response from server!");
                            try {
                                Log.d(TAG, "onNext: " + responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: ", e);
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: called.");
                        }
                    });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}