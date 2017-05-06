package com.lansum.eip.activity.homefragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.lansum.eip.R;
import com.lansum.eip.activity.MainActivity;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ToastStudio;
import com.lansum.eip.view.CommItemView;
import com.litesuits.common.data.DataKeeper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserSettings extends AppCompatActivity {

    @BindView(R.id.user_settings_toolbar)
    Toolbar setttingToolbar;

    @BindView(R.id.item_change_password)
    CommItemView changePwd;

    @BindView(R.id.item_about)
    CommItemView aboutWe;

    @BindView(R.id.item_cache)
    CommItemView appCache;

    //ios风格开关
    @BindView(R.id.switch_ios)
    SwitchView switchView;

    @BindView(R.id.exit_login)
    Button loginSeekBar;

    @BindView(R.id.setting_back_img)
    ImageView backImage;

    private PopupWindow mPopupWindow;


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
        setContentView(R.layout.activity_user_settings);
        ButterKnife.bind(this);


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

    @OnClick({R.id.item_change_password,R.id.item_about,R.id.exit_login,R.id.item_cache,R.id.setting_back_img})
    void onClick(View view){
        switch (view.getId()){
            case R.id.setting_back_img:
                finish();
                break;
            case R.id.item_change_password: //进入修改密码
                Intent intentPwd = new Intent(UserSettings.this, NewWebViewActivity.class);
                intentPwd.putExtra("url", Constants.urlHostBase + Constants.urlChangePwd);
                intentPwd.putExtra("animation",R.anim.slide_right_out);
                startActivity(intentPwd);
                //从右往左进入
                overridePendingTransition(R.anim.slide_right_in, R.anim.none);
                break;
            case R.id.item_about:  //进入关于页面
                Intent intentAbout = new Intent(UserSettings.this, NewWebViewActivity.class);
                intentAbout.putExtra("url", Constants.urlHostBase + Constants.urlAboutUS);
                intentAbout.putExtra("animation",R.anim.slide_right_out);
                startActivity(intentAbout);
                //从右往左进入
                overridePendingTransition(R.anim.slide_right_in, R.anim.none);
                break;
            case R.id.item_cache:  //清理缓存
                SweetAlertDialog pDialogCache = new SweetAlertDialog(UserSettings.this, SweetAlertDialog.WARNING_TYPE);
                //确认操作弹框（不带取消）
                pDialogCache.setTitleText("确定清理缓存吗?")
                        //.setContentText("缓存让加载速度更快!")
                        .setConfirmText("是的，清理它")
                        .setCancelText("NO，点错了！")
                        .showCancelButton(false)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {  //取消
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();  //隐藏对话框
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                //点击确定执行的操作
                                new SweetAlertDialog(UserSettings.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("清理成功!")
                                        .setContentText("您已成功清理了缓存!")
                                        .show();
                                sDialog.cancel();
                            }
                        })
                        .show();
                break;
            case R.id.exit_login:  //退出登录
                showBottomPopupWindow();
                break;
        }
    }

    /**
     * 建立PopupWindow
     */
    private void showBottomPopupWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.layout_popup_bottom, null);
        mPopupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置可以获取焦点，否则弹出菜单中的EditText是无法获取输入的
        mPopupWindow.setFocusable(true);
        //防止虚拟软键盘被弹出菜单遮住
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置背景颜色
        mPopupWindow.setAnimationStyle(R.style.Animation_Bottom_Dialog);//设置动画
        popView.findViewById(R.id.btn_camera_album).setOnClickListener(onClickListener);
        popView.findViewById(R.id.btn_camera_cancel).setOnClickListener(onClickListener);
        //mContent = (EditText) popView.findViewById(R.id.edit_content);
        //参数1:根视图，整个Window界面的最顶层View  参数2:显示位置
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置PopupWindow的点击事件
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_camera_album:
                    mPopupWindow.dismiss();
                    Intent intent = new Intent(UserSettings.this,NewWebViewActivity.class);
                    intent.putExtra("url",Constants.urlHostBase + Constants.urlLogIn);
                    startActivity(intent);
                    break;
                case R.id.btn_camera_cancel:
                    mPopupWindow.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}
