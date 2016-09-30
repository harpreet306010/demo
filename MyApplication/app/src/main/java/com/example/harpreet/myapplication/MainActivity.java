package com.example.harpreet.myapplication;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mActionBarDate;
    private boolean mDateBrowseCycleComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView webView = (WebView) findViewById(R.id.browse_webview);
        Button browerGoBtn = (Button) findViewById(R.id.browser_button);
        final EditText browserInputText = (EditText) findViewById(R.id.input_url);

        if (browerGoBtn != null) {
            browerGoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView != null && browserInputText != null && !TextUtils.isEmpty(browserInputText.getText().toString())) {
                        WebSettings settings = webView.getSettings();
                        settings.setJavaScriptEnabled(true);
                        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                        webView.setWebViewClient(new WebViewClient());
                        webView.loadUrl("http://" + browserInputText.getText().toString());
                    }
                }
            });
        }

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View mCustomView = inflater.inflate(R.layout.actionbar_layout, null);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);

            View calanderBrowse = (View) mCustomView.findViewById(R.id.calander_browse);

            mActionBarDate = (TextView) mCustomView.findViewById(R.id.calander_date);

            if (calanderBrowse != null) {
                calanderBrowse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateTimePicker();
                    }
                });
            }
        }
    }

    private void showDateTimePicker() {
        final java.util.Calendar myCalendar = java.util.Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(java.util.Calendar.YEAR, year);
                myCalendar.set(java.util.Calendar.MONTH, monthOfYear);
                myCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }


        };

        new DatePickerDialog(MainActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(java.util.Calendar myCalendar) {
        String myFormat = "MMMM/dd/yy";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(myFormat, Locale.US);
        if (mActionBarDate != null) {
            if (mDateBrowseCycleComplete) {
                mActionBarDate.setText(mActionBarDate.getText().toString() + " to " + sdf.format(myCalendar.getTime()));
            } else {
                mActionBarDate.setText(sdf.format(myCalendar.getTime()));
            }

        }

        if (!mDateBrowseCycleComplete) {
            mDateBrowseCycleComplete = true;
            showDateTimePicker();
        }
    }

}
