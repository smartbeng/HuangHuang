package com.lansum.eip.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lansum.eip.R;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.webview.WebViewController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    public static Activity loginActivity;

    @BindView(R.id.login_web_view)
    WebViewController loginWebView;

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loginActivity=this;
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (isLogin){
            loginWebView.loadUrl(Constants.urlHostBase + Constants.urlLogIn);

        }else {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            isLogin = true;
        }
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(loginActivity);
        super.onDestroy();
    }
}
