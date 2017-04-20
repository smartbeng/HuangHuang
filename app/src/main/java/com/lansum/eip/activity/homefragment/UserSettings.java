package com.lansum.eip.activity.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lansum.eip.R;
import com.lansum.eip.activity.BaseActivity;
import com.lansum.eip.activity.homefragment.usersettings.AboutActivity;
import com.lansum.eip.activity.homefragment.usersettings.ChangePwdActivity;
import com.lansum.eip.view.CommItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;

public class UserSettings extends BaseActivity {

    @BindView(R.id.user_settings_toolbar)
    Toolbar setttingToolbar;

    @BindView(R.id.item_change_password)
    CommItemView changePwd;

    @BindView(R.id.item_about)
    CommItemView aboutWe;

    //ios风格开关
    @BindView(R.id.switch_ios)
    SwitchView switchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        ButterKnife.bind(this);
        // 设置 toolbar 显示的标题
        setttingToolbar.setTitle("设置");
        // 添加返回的箭头
        setttingToolbar.setNavigationIcon(R.drawable.fanhui);
        // 让toolbar继承actionBar的属性
        setSupportActionBar(setttingToolbar);

        /**
         * 设置点击返回箭头的事件  一定要放在 setSupportActionBar 之后设置
         */
        setttingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击箭头 关闭当前的activity
                finish();
            }
        });

        /**
         * 消息提醒（ios风格开关）点击事件
         */
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
            }
        });

    }

    @OnClick({R.id.item_change_password,R.id.item_about})
    void onClick(View view){
        switch (view.getId()){
            case R.id.item_change_password:
                startActivity(new Intent(UserSettings.this, ChangePwdActivity.class));
                break;
            case R.id.item_about:
                startActivity(new Intent(UserSettings.this, AboutActivity.class));
                break;
        }
    }

}
