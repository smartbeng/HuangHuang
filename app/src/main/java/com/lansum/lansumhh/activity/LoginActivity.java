package com.lansum.lansumhh.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lansum.lansumhh.R;
import com.lansum.lansumhh.http.Constants;
import com.lansum.lansumhh.webview.WebViewController;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    public static Activity loginActivity;

    @BindView(R.id.login_web_view)
    WebViewController loginWebView;

    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity=this;
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (isLogin){
            loginWebView.loadUrl(Constants.urlHostBase + Constants.urlLogIn);
            isLogin = false;
        }else {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}
