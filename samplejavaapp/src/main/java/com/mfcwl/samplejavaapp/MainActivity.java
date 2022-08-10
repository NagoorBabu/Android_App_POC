package com.mfcwl.samplejavaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] temp = new String[2];
        temp[1] = "123";
        if (temp.length >= 3 && temp[1] != null)
            temp[1] = null;

        Log.e(TAG, "onCreate: " + temp[1]);

/*
        TextView textView = findViewById(R.id.text_view);

        String[] mobileNumbers = parseNumbersToArray(null);
        if (mobileNumbers != null && mobileNumbers.length > 0) {
            textView.setText(String.join(", ", mobileNumbers));
            for (String number : mobileNumbers)
                setUnderLineText(textView, number);
        }
        mobileNumbers = parseNumbersToArray("9345326966");
        if (mobileNumbers != null && mobileNumbers.length > 0) {
            textView.setText(String.join(", ", mobileNumbers));
            for (String number : mobileNumbers)
                setUnderLineText(textView, number);
        }
        mobileNumbers = parseNumbersToArray("9345326966 / 9962318083 / 7904701633 / 9003068096");
        if (mobileNumbers != null && mobileNumbers.length > 0) {
            textView.setText(String.join(", ", mobileNumbers));
            for (String number : mobileNumbers)
                setUnderLineText(textView, number);
        }
*/
    }

    private class CustomClickableSpan extends ClickableSpan {
        String text;

        CustomClickableSpan(String text) {
            this.text = text;
        }

        @Override
        public void onClick(@NonNull View view) {

        }
    }

    public void setUnderLineText(TextView tv, String textToUnderLine) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToUnderLine, 0);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.purple_700));
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ClickableSpan clickableSpan = new CustomClickableSpan(textToUnderLine) {
            @Override
            public void onClick(View textView) {
                // do some thing

                Toast.makeText(MainActivity.this, "Clicked" + text, Toast.LENGTH_SHORT).show();
            }
        };
        SpannableString wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToUnderLine, ofs);
            if (ofe == -1)
                break;
            else {
                wordToSpan.setSpan(clickableSpan, ofe, ofe + textToUnderLine.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordToSpan.setSpan(foregroundColorSpan, ofe, ofe + textToUnderLine.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordToSpan.setSpan(underlineSpan, ofe, ofe + textToUnderLine.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setHighlightColor(Color.TRANSPARENT);
            }
        }
    }

    private String[] parseNumbersToArray(String numbers) {
        String[] numberArray = null;
        if (numbers != null && numbers.length() > 0) {
            numberArray = numbers.split("/");
        }
        if (numberArray != null) {
            String[] trimmedArray = new String[numberArray.length];
            for (int i = 0; i < numberArray.length; i++)
                trimmedArray[i] = numberArray[i].trim();
            Log.e(TAG, Arrays.toString(trimmedArray));
            return trimmedArray;
        }
        return null;
    }
}