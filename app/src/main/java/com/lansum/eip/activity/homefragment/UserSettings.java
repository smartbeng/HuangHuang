package com.lansum.eip.activity.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lansum.eip.R;
import com.lansum.eip.activity.LoginActivity;
import com.lansum.eip.activity.homefragment.usersettings.AboutActivity;
import com.lansum.eip.activity.homefragment.usersettings.ChangePwdActivity;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.HelperSP;
import com.lansum.eip.util.ToastStudio;
import com.lansum.eip.view.CommItemView;
import com.litesuits.common.data.DataKeeper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;

public class UserSettings extends AppCompatActivity {

    @BindView(R.id.user_settings_toolbar)
    Toolbar setttingToolbar;

    @BindView(R.id.item_change_password)
    CommItemView changePwd;

    @BindView(R.id.item_about)
    CommItemView aboutWe;

    //ios风格开关
    @BindView(R.id.switch_ios)
    SwitchView switchView;

    @BindView(R.id.exit_login)
    Button loginSeekBar;


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
            DataKeeper dataKeeper = new DataKeeper(UserSettings.this,"switch");
            Boolean isOpen;
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                ToastStudio.showToast(UserSettings.this,"打开");
                isOpen = true;
                dataKeeper.put("switchViewTrue",isOpen);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                ToastStudio.showToast(UserSettings.this,"关闭");
                dataKeeper.put("switchViewFalse",isOpen);
            }
        });
    }

    @OnClick({R.id.item_change_password,R.id.item_about,R.id.exit_login})
    void onClick(View view){
        switch (view.getId()){
            case R.id.item_change_password:
                startActivity(new Intent(UserSettings.this, ChangePwdActivity.class));
                break;
            case R.id.item_about:
                startActivity(new Intent(UserSettings.this, AboutActivity.class));
                break;
            case R.id.exit_login:
                Snackbar.make(view,"确定退出登录吗？",Snackbar.LENGTH_SHORT)
                        .setAction("好", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                startActivity(new Intent(UserSettings.this, LoginActivity.class));
                            }
                        })
                        .show();
                break;
        }
    }
}
