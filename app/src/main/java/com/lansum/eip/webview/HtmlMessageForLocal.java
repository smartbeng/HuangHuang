package com.lansum.eip.webview;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lansum.eip.activity.BaseActivity;
import com.lansum.eip.activity.LoginActivity;
import com.lansum.eip.activity.MainActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.CookieUtil;
import com.litesuits.common.data.DataKeeper;
import com.taobao.agoo.ICallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;


/**
 * 本地与WebView交互类
 * 1.根据点击的WebView位置服务端发送相应接口名
 * 2.本地去实现这些接口
 * Created by shiYunPeng on 2017/4/14.
 */

public class HtmlMessageForLocal {

    private BaseActivity baseActivity;


    //网页根据点击的WebView反馈的接口名
    private static final String TAG = "MehtodName";
    //声明Handler
    private Handler mHandler = new Handler();

    // web调用android
    @JavascriptInterface
    public void callHandler(final String methodName, final String data, final String callbackName) {
        Log.e(TAG, "Method:" + methodName);

        if (methodName.equals("setPasswordFromJS")) {

            DataKeeper dataKeeper = new DataKeeper(LoginActivity.loginActivity,"HH");
            dataKeeper.put("UserPwd",data);

        } else if (methodName.equals("loginSuccessFromJS")) {  // 登录成功
            //打开登陆页面 并获取cookie
            loginSuccessFromJS();

            // 打开新窗口
        } /*else if (methodName.equals("openAttendanceFromJS")) {
            openAttendanceFromJS(data);
            // 设置抬头
        } else if (methodName.equals("setTitleFromJS")) {
            setTitleFromJS(data);
            // 从右往左推出页面
        } else if (methodName.equals("pushViewControllerFromJS")) {
            pushViewControllerFromJS(data);
            // 添加右上角按钮
        } else if (methodName.equals("addRightBarButtonItemFromJS")) {
            addRightBarButtonItemFromJS(data);
            // 从下往上弹出
        } else if (methodName.equals("presentViewControllerFromJS")) {
            presentViewControllerFromJS(data);
            // 添加左上角按钮
        } else if (methodName.equals("addLeftBarButtonItemFromJS")) {
            addLeftBarButtonItemFromJS(data);
            // 关闭web
        } else if (methodName.equals("dismissViewControllerFromJS")) {
            dismissViewControllerFromJS();
            // 调用页面刷新
        } else if (methodName.equals("postNotificationFromJS")) {
            postNotificationFromJS(data);
        } else if (methodName.equals("popViewControllerFromJS")) {
            popViewControllerFromJS();
            *//**获取开门图片**//*
        } else if (methodName.equals("getOpenDoorImageFromJS")) {
            getOpenDoorImageFromJS();
            *//**开门**//*
        } else if (methodName.equals("openDoorFromJS")) {
            openDoorFromJS();
            *//**添加右上角按钮菜单**//*
        } else if (methodName.equals("addDropMenuFromJS")) {
            addDropMenuFromJS(data);
            *//**定时提醒通知**//*
        } else if (methodName.equals("addLocalNotificationFromJS")) {
            addLocalNotificationFromJS(data);
            *//**取消提醒通知**//*
        }else if (methodName.equals("cancelLocalNotificationFromJS")) {
            cancelLocalNotificationFromJS(data);
        } /*else {
            Log.d("js", "Method not found:" + methodName);
        }*/
    }

    /**
     * 登陆成功
     * @param userId
     */
    String userCookieName = "UserInfo";
    public String filePath = "";
    @JavascriptInterface
    private void loginSuccessFromJS() {

        /**
         * getFilesDir()方法用于获取/data/data/<application package>/files目录
         * getAbsolutePath()：返回抽象路径名的绝对路径名字符串。
         */
        filePath = LoginActivity.loginActivity.getApplicationContext().getFilesDir().getAbsolutePath();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "loginSuccess");
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(Constants.urlHostBase);
                //获取Cookie帮助类
                CookieUtil cookieUtil = new CookieUtil();
                String name = cookieUtil.GetCookieParamInt(CookieStr, "realName");
                String jobName = cookieUtil.GetCookieParamInt(CookieStr, "jobName");

                // 从cookie中获取userID
                final String UserId = cookieUtil.GetCookieParamInt(CookieStr, "userId");
                String userVers = cookieUtil.GetCookieParamInt(CookieStr, "userVers");

                // 保存字段
                DataKeeper dataKeeper = new DataKeeper(LoginActivity.loginActivity.getApplicationContext(),"HH");
                dataKeeper.put("UserId",UserId);
                dataKeeper.put("UserVers",userVers);
                dataKeeper.put("name",name);
                dataKeeper.put("JobName",jobName);
                final String pwd = dataKeeper.get("UserPwd","");
                LoginActivity.loginActivity.getResources();

                baseActivity.mPushAgent.addExclusiveAlias(UserId, "HuangHuang", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Toast.makeText(baseActivity, "............", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Intent intent = new Intent(LoginActivity.loginActivity, MainActivity.class);
        intent.putExtra("pull", true);
        intent.putExtra("splashEnable", 1);
        LoginActivity.loginActivity.startActivity(intent);
        LoginActivity.loginActivity.finish();
    }

    /**
     * 打开新窗口
     * @param url
     */



}
