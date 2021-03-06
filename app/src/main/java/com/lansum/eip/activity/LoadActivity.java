package com.lansum.eip.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lansum.eip.R;
import com.lansum.eip.util.ActivityCollector;

public class LoadActivity extends AppCompatActivity {

    private static final String TAG = "LoadActivity";
    public LoadActivity loadActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Log.e(TAG, "****被创建");

        /**
         * 开启线程让欢迎页面休眠1秒后再跳转
         */
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(LoadActivity.this,LoginActivity.class));
                finish();
            }
        }.start();
    }

    /**
     * 隐藏状态栏
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart: 来了");
        super.onStart();
    }

    @Override
    protected void onStop() {
        ActivityCollector.removeActivity(loadActivity);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(loadActivity);
        super.onDestroy();
    }
}
