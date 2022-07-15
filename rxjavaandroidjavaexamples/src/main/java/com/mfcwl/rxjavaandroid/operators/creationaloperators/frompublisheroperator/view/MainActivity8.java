package com.mfcwl.rxjavaandroid.operators.creationaloperators.frompublisheroperator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.mfcwl.rxjavaandroid.R;

import java.io.IOException;

import okhttp3.ResponseBody;

/*fromPublisher() is used to convert LiveData objects to reactive streams, or from reactive streams to LiveData objects.

More than likely you will never want to convert LiveData to a reactive Observable - other than some rare cases when you might want to apply an operator.

On the other hand, converting an Observable to LiveData is actually very practical. There is many use-case for that. The example below makes a network request using Retrofit and retrieves a response in the form of a Flowable object. Flowable implements the Publisher interface. Publisher objects can be subscribed to just like Observables. I will be writing a lecture on Flowables and how they're different than Observables. For now just think of it as an Observable.


Input: LiveData<T>
Ouput: Observable<T>


OR

Input: Observable<T>
Ouput: LiveData<T>

*/
public class MainActivity8 extends AppCompatActivity {

    private static final String TAG = "MainActivity8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        Main8ViewModel viewModel = ViewModelProviders.of(this).get(Main8ViewModel.class);
        viewModel.makeQuery().observe(this, new androidx.lifecycle.Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                Log.d(TAG, "onChanged: this is a live data response!");
                try {
                    Log.d(TAG, "onChanged: " + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}