package com.lansum.eip.webview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by MaiBenBen on 2017/4/12.
 */

public class WebViewScrollChanged extends WebView {

    ScrollInterface web;

    public WebViewScrollChanged(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WebViewScrollChanged(Context context) {
        super(context);
    }

    public WebViewScrollChanged(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebViewScrollChanged(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(web!=null)
            web.onSChanged(l, t, oldl, oldt);
    }

    /**
     * 滑动接口
     */
    public interface ScrollInterface {
         void onSChanged(int l, int t, int oldl, int oldt);
    }
}
