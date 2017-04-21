package com.lansum.eip.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lansum.eip.R;
import com.lansum.eip.util.ActivityCollector;

public class LoadActivity extends AppCompatActivity {

    public LoadActivity loadActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

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

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(loadActivity);
        super.onDestroy();
    }
}
