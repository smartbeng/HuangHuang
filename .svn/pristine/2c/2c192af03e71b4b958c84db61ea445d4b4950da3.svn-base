package com.lansum.eip.util;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by MaiBenBen on 2017/4/26.
 */

public class StatusBarUtil extends AppCompatActivity{

    public StatusBarUtil(){}

    public static void hideStatusBar(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = ActivityCollector.getTopActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            ActivityCollector.getTopActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
