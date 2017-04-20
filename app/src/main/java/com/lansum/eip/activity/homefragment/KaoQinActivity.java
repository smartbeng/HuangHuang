package com.lansum.eip.activity.homefragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.lansum.eip.R;
import com.lansum.eip.http.Constants;

import butterknife.BindView;

public class KaoQinActivity extends AppCompatActivity {

    @BindView(R.id.user_kaoqin_web)
    WebView kaoQinWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kao_qin);

        kaoQinWeb.loadUrl(Constants.urlHostBase + Constants.urlAttendance);
    }
}
