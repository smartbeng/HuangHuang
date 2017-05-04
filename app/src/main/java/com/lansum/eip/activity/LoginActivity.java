package com.lansum.eip.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lansum.eip.R;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.Const;
import com.lansum.eip.webview.WebViewController;
import com.litesuits.common.data.DataKeeper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loginActivity=this;
        Log.e(TAG, "*******被创建" );

        DataKeeper dataKeeper = new DataKeeper(this,"HH");
        String userId = dataKeeper.get("UserId","");
        String userVers = dataKeeper.get("UserVers","");
        String name =dataKeeper.get("Name", "");
        String jobName = dataKeeper.get("JobName", "");



        if (!userId.equals("")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else {   //没登陆
            Intent intent = new Intent(LoginActivity.this,NewWebViewActivity.class);
            intent.putExtra("url", Constants.urlHostBase + Constants.urlLogIn);
            startActivity(intent);
        }


        finish();
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
        ActivityCollector.removeActivity(ActivityCollector.getTopActivity());
        Log.e(TAG, "停止了！！！！");
        finish();
        super.onStop();

    }

    @Override
    protected void onResume() {
        Log.e(TAG, "来交互啊啊啊啊啊");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
