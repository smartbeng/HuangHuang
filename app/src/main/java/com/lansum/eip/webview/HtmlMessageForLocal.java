package com.lansum.eip.webview;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lansum.eip.R;
import com.lansum.eip.activity.MainActivity;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.activity.homefragment.KaoQinActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.model.RightInfo;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.CookieUtil;
import com.litesuits.common.data.DataKeeper;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import static com.lansum.eip.R.id.image;

/**
 * 本地与WebView交互类
 * 1.根据点击的WebView位置服务端发送相应接口名
 * 2.本地去实现这些接口
 * Created by shiYunPeng on 2017/4/14.
 */

public class HtmlMessageForLocal {

    //网页根据点击的WebView反馈的接口名
    private static final String TAG = "MehtodName";
    //声明Handler
    private Handler mHandler;

    // web调用android
    @JavascriptInterface
    public void callHandler(final String methodName, final String data, final String callbackName) {
        Log.e(TAG, "Method:" + methodName);

        if (methodName.equals("setPasswordFromJS")) {

            DataKeeper dataKeeper = new DataKeeper(ActivityCollector.getTopActivity(), "HH");
            dataKeeper.put("UserPwd", data);

        } else if (methodName.equals("loginSuccessFromJS")) {  // 登录成功
            //打开登陆页面 并获取cookie
            loginSuccessFromJS();
            // 打开新窗口
        } else if (methodName.equals("openAttendanceFromJS")) {
            openAttendanceFromJS(data);
            // 设置抬头
        } else if (methodName.equals("setTitleFromJS")) {
            setTitleFromJS(data);
            // 从右往左推出页面
        } else if (methodName.equals("pushViewControllerFromJS")) {
            pushViewControllerFromJS(data);
            // 添加右上角按钮
        }  else if (methodName.equals("addRightBarButtonItemFromJS")) {
            addRightBarButtonItemFromJS(data);
            // 从下往上弹出
        }

        else if (methodName.equals("presentViewControllerFromJS")) {
            presentViewControllerFromJS(data);
            // 添加左上角按钮
        }/* else if (methodName.equals("addLeftBarButtonItemFromJS")) {
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
     *
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
        filePath = ActivityCollector.getTopActivity().getApplicationContext().getFilesDir().getAbsolutePath();
        mHandler = new Handler();
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
                DataKeeper dataKeeper = new DataKeeper(ActivityCollector.getTopActivity().getApplicationContext(), "HH");
                dataKeeper.put("UserId", UserId);
                dataKeeper.put("UserVers", userVers);
                dataKeeper.put("name", name);
                dataKeeper.put("JobName", jobName);
                final String pwd = dataKeeper.get("UserPwd", "");
                //友盟推送
                PushAgent mPushAgent = PushAgent.getInstance(ActivityCollector.getTopActivity());

                mPushAgent.addExclusiveAlias(UserId, "HuangHuang", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Toast.makeText(ActivityCollector.getTopActivity(), "............", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Intent intent = new Intent(ActivityCollector.getTopActivity(), MainActivity.class);
        intent.putExtra("pull", true);
        intent.putExtra("splashEnable", 1);
        ActivityCollector.getTopActivity().startActivity(intent);
        ActivityCollector.getTopActivity().finish();
    }

    /**
     * 添加右上角按钮
     *
     * @param
     */
    private ImageView topRight;

    private void addRightBarButtonItemFromJS(String data) {
        Gson gson = new Gson();
        RightInfo rightInfo = gson.fromJson(data, RightInfo.class);
        if (!rightInfo.image.equals("")) {
            ActivityCollector.getTopActivity().getResources();
            int imageId = ActivityCollector.getTopActivity().getResources().getIdentifier(rightInfo.image.toLowerCase(), "drawable",
                    ActivityCollector.getTopActivity().getPackageName());
            if (imageId == 0) {
                Log.e(TAG, "*************rightInfo.image*****找不到");
            } else {
                Log.e(TAG, "*************rightInfo.image*****找到了");
                ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        topRight = (ImageView) ActivityCollector.getTopActivity().findViewById(R.id.right_Button);
                        //设置右上角按钮
                        topRight.setBackgroundResource(imageId);
                        topRight.setVisibility(View.VISIBLE);
                        topRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WebView baseWebView = (WebView) ActivityCollector.getTopActivity().findViewById(R.id.base_web_view);
                                baseWebView.loadUrl("javascript:" +rightInfo.funcName);
                            }
                        });
                    }
                });
            }
        }

    }

    // 抬头
    private TextView txtTop;

    // 设置抬头
    protected void setTitleFromJS(final String title) {
        ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtTop = (TextView) ActivityCollector.getTopActivity().findViewById(R.id.topText);
                // 设置顶部文字
                if (title != null && txtTop != null) {
                    txtTop.setText(title);
                }
            }
        });
    }

    protected void pushViewControllerFromJS(String data){
        openAttendanceFromJS(data);
    }

    protected void openAttendanceFromJS(String url) {
        Log.i("js", url);
        // TODO Auto-generated method stub
        /*openNewWindow(url, false, false, 2, "");*/
        Intent intent = new Intent(ActivityCollector.getTopActivity(), NewWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("animation",R.anim.slide_right_out);
        ActivityCollector.getTopActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.none);
        ActivityCollector.getTopActivity().startActivity(intent);
    }

    protected void presentViewControllerFromJS(String data){
        Intent intent = new Intent(ActivityCollector.getTopActivity(), NewWebViewActivity.class);
        intent.putExtra("url", data);
        intent.putExtra("animation",R.anim.push_bottom_out);
        ActivityCollector.getTopActivity().overridePendingTransition(R.anim.push_bottom_in, R.anim.none);
        ActivityCollector.getTopActivity().startActivity(intent);
    }

}

