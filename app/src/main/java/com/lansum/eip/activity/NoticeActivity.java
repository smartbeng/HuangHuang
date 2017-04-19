package com.lansum.eip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lansum.eip.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeActivity extends AppCompatActivity {

    @BindView(R.id.notice_toolbar)
    Toolbar noticeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        // 设置 toolbar 显示的标题
        noticeToolbar.setTitle("通知");
        // 添加返回的箭头
        noticeToolbar.setNavigationIcon(R.drawable.fanhui);
        // 让toolbar继承actionBar的属性
        setSupportActionBar(noticeToolbar);

        /**
         * 设置点击返回箭头的事件  一定要放在 setSupportActionBar 之后设置
         */
        noticeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击箭头 关闭当前的activity
                finish();
            }
        });
    }
}
