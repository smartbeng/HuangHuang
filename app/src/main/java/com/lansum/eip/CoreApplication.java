package com.lansum.eip;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.AppHolder;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import static anet.channel.util.Utils.context;

/**
 * Created by Administrator on 2017/2/21.
 * <p>
 * 加载全局信息
 * <p>
 * 一定要在 manifest 文件的application节点中的name属性中配置
 */
public class CoreApplication extends Application {

    private static final String TAG = "进入注册成功提示";

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 Context 对象
        AppHolder.init(getApplicationContext());
        //声明推送类
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(false);
        //友盟统计应用启动数据
        PushAgent.getInstance(context).onAppStart();
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) { //友盟推送注册成功
                //注册成功会返回device token
                Log.e(TAG, "注册成功:" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {  //注册失败
                Log.e(TAG, "注册失败:" + s+ "失败了" +s1);
            }
        });

        /**
         * Activity 声明周期回调
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                ActivityCollector.addActivity(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }


            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityCollector.removeActivity(activity);
            }
        });

    }
}
