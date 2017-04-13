package com.lansum.lansumhh.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 加强类
 * Created by shiYunpeng on 2017/4/12.
 */

public class ToastStudio {

    private static Toast toast;
    public static void showToast(Context context, String content){
        if (toast == null){
            toast =  Toast.makeText(context,content, Toast.LENGTH_SHORT);
        }else {
            toast.setText(content);
        }
        toast.show();
    }
}
