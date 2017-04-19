package com.lansum.eip;

import android.app.Application;
import android.util.Log;

import com.lansum.eip.util.AppHolder;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

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

    }
}
