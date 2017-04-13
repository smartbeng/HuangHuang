package com.lansum.lansumhh;

import android.app.Application;

import com.lansum.lansumhh.util.AppHolder;

/**
 * Created by Administrator on 2017/2/21.
 * <p>
 * 加载全局信息
 * <p>
 * 一定要在 manifest 文件的application节点中的name属性中配置
 */

public class CoreApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 Context 对象
        AppHolder.init(getApplicationContext());
    }
}
