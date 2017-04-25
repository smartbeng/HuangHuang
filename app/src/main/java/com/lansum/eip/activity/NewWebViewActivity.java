package com.lansum.eip.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lansum.eip.BaseActivity;
import com.lansum.eip.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewWebViewActivity extends BaseActivity {

    @BindView(R.id.base_web_view)
    WebView baseWebView;

    @BindView(R.id.right_Button)
    ImageView rightImageView;

    @BindView(R.id.toolbar_text_top)
    TextView tittleText;

    @BindView(R.id.back_web)
    ImageView backImageView;

    @BindView(R.id.right_Button_text)
    TextView rightButtonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_web_view);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        int anim = getIntent().getIntExtra("animation",0);

        if (url == null || anim == 0){
            finish();
        }else{
            baseWebView.loadUrl(url);
            int id = 0;
            if (anim == R.anim.slide_right_out){
                id = R.drawable.fanhui;
            }else if(anim == R.anim.push_bottom_out){
               id = R.drawable.navbar_close;
            }
            backImageView.setImageResource(id);
            backImageView.setVisibility(View.VISIBLE);



        }
    }
    @OnClick({R.id.back_web})
    void back(){
        finish();
    }



}
