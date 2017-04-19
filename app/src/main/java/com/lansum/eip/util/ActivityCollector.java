package com.lansum.eip.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity 管理类
 * Created by MaiBenBen on 2016/12/28.
 */
public class ActivityCollector {

    private static List<Activity> activityList = new ArrayList<>();  //用来管理所有的activity

    /**
     * 添加当前的Activity到栈中
     * @param activity
     */
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 将当前Activity移出返回栈
     * @param activity
     */
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    /**
     * 获取到栈顶的Activity
     * @return
     */
    public static Activity getTopActivity(){
        if (activityList.isEmpty()){
            return null;
        }else {
            return activityList.get(activityList.size() - 1);
        }
    }
}
