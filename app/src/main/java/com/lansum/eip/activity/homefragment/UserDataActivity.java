package com.lansum.eip.activity.homefragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.lansum.eip.BaseActivity;
import com.lansum.eip.R;
import com.lansum.eip.http.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDataActivity extends BaseActivity {

    @BindView(R.id.user_data_toolbar)
    Toolbar userDataToolbar;

    @BindView(R.id.user_data_web)
    WebView userDataWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        ButterKnife.bind(this);
        // 设置 toolbar 显示的标题
        userDataToolbar.setTitle("通知");
        // 添加返回的箭头
        userDataToolbar.setNavigationIcon(R.drawable.fanhui);
        // 让toolbar继承actionBar的属性
        setSupportActionBar(userDataToolbar);

        /**
         * 设置点击返回箭头的事件  一定要放在 setSupportActionBar 之后设置
         */
        userDataToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击箭头 关闭当前的activity
                finish();
            }
        });
        userDataWeb.loadUrl(Constants.urlHostBase + Constants.urlPersonal);
    }
}
