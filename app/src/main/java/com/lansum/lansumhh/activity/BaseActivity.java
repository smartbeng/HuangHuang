package com.lansum.lansumhh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.lansum.lansumhh.webview.WebViewController;

/**
 * Created by MaiBenBen on 2017/4/12.
 */

public class BaseActivity extends AppCompatActivity {

    public static Activity baseActivity;
    
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        baseActivity = this;
        super.onCreate(savedInstanceState, persistentState);
    }
}



