package com.lansum.eip.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by MaiBenBen on 2017/4/12.
 *
 * 当前项目的 一部分共变量 持有者
 *
 */

public class AppHolder {

    private static Context mContext;


    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Resources getResources() {
        return mContext.getResources();
    }

}
