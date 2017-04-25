package com.lansum.eip.activity.homefragment.usersettings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.lansum.eip.BaseActivity;
import com.lansum.eip.R;
import com.lansum.eip.http.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.about_toolbar)
    Toolbar aboutToolbar;

    @BindView(R.id.about_web)
    WebView aboutWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        // 设置 toolbar 显示的标题
        aboutToolbar.setTitle("通知");
        // 添加返回的箭头
        aboutToolbar.setNavigationIcon(R.drawable.fanhui);
        // 让toolbar继承actionBar的属性
        setSupportActionBar(aboutToolbar);

        /**
         * 设置点击返回箭头的事件  一定要放在 setSupportActionBar 之后设置
         */
        aboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击箭头 关闭当前的activity
                finish();
            }
        });
        aboutWeb.loadUrl(Constants.urlHostBase + Constants.urlAboutUS);

    }
}
