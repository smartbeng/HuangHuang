package com.lansum.eip.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lansum.eip.BaseActivity;
import com.lansum.eip.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        /**
         * 隐藏状态栏
         */
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_new_web_view);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        int anim = getIntent().getIntExtra("animation", 0);

        if (url == null || anim == 0) {
            finish();
        } else {
            baseWebView.loadUrl(url);
            //当所有的新开页面 是从右往左打开时
            if (anim == R.anim.slide_right_out) {
                backImageView.setImageResource(R.drawable.returns);
                backImageView.setVisibility(View.VISIBLE);
                backImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        //设置页面退出动画为从左往右退出
                        overridePendingTransition( R.anim.none,R.anim.slide_right_out);
                    }
                });

            }/*else if(anim == R.anim.push_bottom_out){
                rightImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        overridePendingTransition(R.anim.push_bottom_out,R.anim.push_bottom_in);
                    }
                });
            }*/
        }
    }

}
