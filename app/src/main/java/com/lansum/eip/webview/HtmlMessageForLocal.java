package com.lansum.eip.webview;

import android.app.Activity;
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
import com.lansum.eip.http.Constants;
import com.lansum.eip.model.ReFreshInfo;
import com.lansum.eip.model.RightInfo;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.CookieUtil;
import com.litesuits.common.data.DataKeeper;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

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

            loginSuccessFromJS();            //打开登陆页面 并获取cookie

        } else if (methodName.equals("openAttendanceFromJS")) {

            openAttendanceFromJS(data);      // 打开新窗口

        } else if (methodName.equals("setTitleFromJS")) {

            setTitleFromJS(data);             // 设置抬头

        } else if (methodName.equals("addRightBarButtonItemFromJS")) {

            addRightBarButtonItemFromJS(data);// 添加右上角按钮

        } else if (methodName.equals("presentViewControllerFromJS")) {

            presentViewControllerFromJS(data);// 从下往上弹出

        }else if (methodName.equals("addLeftBarButtonItemFromJS")) {

            addLeftBarButtonItemFromJS(data); // 添加左上角按钮

        } else if (methodName.equals("dismissViewControllerFromJS")) {

            dismissViewControllerFromJS();    // 关闭web

        } else if (methodName.equals("postNotificationFromJS")) {

            postNotificationFromJS(data);     // 调用页面刷新

        } /*else if (methodName.equals("popViewControllerFromJS")) {
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

    /**
     * 打开登录页面 并获取 Cookie
     */
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
     * 添加左上角按钮
     * @param data
     */
    private void addLeftBarButtonItemFromJS(String data){
        Gson gson = new Gson();
        RightInfo rightInfo = gson.fromJson(data, RightInfo.class);
        if (!rightInfo.image.equals("")) {
            ActivityCollector.getTopActivity().getResources();
            int imageId = ActivityCollector.getTopActivity().getResources().getIdentifier(rightInfo.image.toLowerCase(), "drawable",
                    ActivityCollector.getTopActivity().getPackageName());
            if (imageId == 0) {
                Toast.makeText(ActivityCollector.getTopActivity(), "没找到左上角资源文件", Toast.LENGTH_SHORT).show();
            }


            Log.e(TAG, "*************rightInfo.image*****找到了");
            ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView topLeftImage = (ImageView) ActivityCollector.getTopActivity().findViewById(R.id.back_web);
                    //设置左上角图片按钮
                    topLeftImage.setBackgroundResource(imageId);
                    topLeftImage.setVisibility(View.VISIBLE);
                    topLeftImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebView baseWebView = (WebView) ActivityCollector.getTopActivity().findViewById(R.id.base_web_view);
                            baseWebView.loadUrl("javascript:" + rightInfo.funcName);
                            //ActivityCollector.getTopActivity().overridePendingTransition(R.anim.push_bottom_in,R.anim.push_bottom_out);

                        }
                    });
                }
            });

        }
    }

    /**
     * 添加右上角按钮
     *
     * @param
     */
    protected void addRightBarButtonItemFromJS(String data) {
        Gson gson = new Gson();
        RightInfo rightInfo = gson.fromJson(data, RightInfo.class);
        if (!rightInfo.image.equals("")) {
            ActivityCollector.getTopActivity().getResources();
            int imageId = ActivityCollector.getTopActivity().getResources().getIdentifier(rightInfo.image.toLowerCase(), "drawable",
                    ActivityCollector.getTopActivity().getPackageName());
            Log.e(TAG, "*************rightInfo.image*****找到了"+imageId+rightInfo.image);
            ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView topRightImage = (ImageView) ActivityCollector.getTopActivity().findViewById(R.id.right_Button);
                    //设置右上角图片按钮
                    topRightImage.setBackgroundResource(imageId);
                    topRightImage.setVisibility(View.VISIBLE);
                    topRightImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebView baseWebView = (WebView) ActivityCollector.getTopActivity().findViewById(R.id.base_web_view);
                            baseWebView.loadUrl("javascript:" + rightInfo.funcName);
                            //ActivityCollector.getTopActivity().overridePendingTransition(R.anim.push_bottom_out, R.anim.push_bottom_in);
                        }
                    });
                }
            });

        } else if (!rightInfo.title.equals("")) {
            ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //设置右上角文字按钮(提交)right_Button_text
                    TextView topRightText = (TextView) ActivityCollector.getTopActivity().findViewById(R.id.right_Button_text);
                    topRightText.setText(rightInfo.title);
                    topRightText.setVisibility(View.VISIBLE);
                    topRightText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebViewController baseWebView = (WebViewController) ActivityCollector.getTopActivity().findViewById(R.id.base_web_view); //提交后开始的地方
                            baseWebView.loadUrl("javascript:" + rightInfo.funcName);
                            baseWebView.isShowLoading(true);

                        }
                    });
                }
            });
        }
    }

    // 设置抬头（WebView中toolbar上的标题）
    protected void setTitleFromJS(final String title) {
        ActivityCollector.getTopActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txtTop = (TextView) ActivityCollector.getTopActivity().findViewById(R.id.toolbar_text_top);
                // 设置顶部文字
                if (title != null && txtTop != null) {
                    txtTop.setText(title);
                }
            }
        });
    }

   /* protected void pushViewControllerFromJS(String data) {
        openAttendanceFromJS(data);
    }*/

    /**
     * 打开新窗口
     * @param url
     */
    protected void openAttendanceFromJS(String url) {
        Log.i("js", url);
        // TODO Auto-generated method stub
        Intent intent = new Intent(ActivityCollector.getTopActivity(), NewWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("animation", R.anim.slide_right_out);
        intent.putExtra("animation", R.anim.slide_right_in);
        ActivityCollector.getTopActivity().startActivity(intent);
        //ActivityCollector.getTopActivity().overridePendingTransition(R.anim.push_bottom_out, R.anim.push_bottom_in);
    }

    /**
     * 调用js方法关闭网页
     */
    protected void dismissViewControllerFromJS(){
        ActivityCollector.getTopActivity().finish();
        ActivityCollector.getTopActivity().overridePendingTransition(R.anim.none, R.anim.push_bottom_out);
    }

    /**
     * 调用页面刷新
     * * 当添加的右上角按钮为文字时网页会调这个方法去进行页面刷新
     * @param data
     */
    protected void postNotificationFromJS(String data){
        Gson gson = new Gson();
        ReFreshInfo a = gson.fromJson(data,ReFreshInfo.class);
        Intent intent = new Intent();
        //指定接收方所要匹配的参数
        intent.setAction(a.notificationName);
        //将加载的LoadData（true）传到更新页面的方法中
        intent.putExtra("name",a.funcName);
        //发送广播通知界面更新
        ActivityCollector.getTopActivity().sendBroadcast(intent);

        Toast.makeText(ActivityCollector.getTopActivity().getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();

    }

    /**
     * 从下往上弹出
     * @param data
     */
    protected void presentViewControllerFromJS(String data) {
        Intent intent = new Intent(ActivityCollector.getTopActivity(), NewWebViewActivity.class);
        intent.putExtra("url", data);
        intent.putExtra("animation", R.anim.push_bottom_out);
        Activity activity =  ActivityCollector.getTopActivity();
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_bottom_in, R.anim.none);
    }

}

