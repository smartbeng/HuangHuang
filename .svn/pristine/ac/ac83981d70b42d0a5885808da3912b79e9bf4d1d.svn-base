package com.lansum.eip.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import android.os.RecoverySystem;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lansum.eip.R;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.activity.mainfragment.DoorActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.ToastStudio;
import com.wang.avi.AVLoadingIndicatorView;

import anetwork.channel.NetworkCallBack;

import static org.android.agoo.common.e.TAG;

/**
 * Created by MaiBenBen on 2017/4/14.
 */

public class WebViewController extends WebView {

    //声明上下文对象
    private Context context;

    //声明WebViewController全局对象
    private WebViewController control;

    //网络未加载完的loading图片
    private ImageView imageView;

    //截取到的网址上的消息头字符串 表示要进入到那个页面
    private String sbFunName = "";

    private ViewGroup parentLayout;

    private View view1;

    public WebViewController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        webviewSettings();
    }

    public WebViewController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        webviewSettings();
    }

    public WebViewController(Context context) {
        super(context);
        webviewSettings();
    }

    @SuppressLint("JavascriptInterface")
    private void webviewSettings() {

        control = this;

        // TODO Auto-generated constructor stub
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(context.getCacheDir().getPath());
        webSettings.setDefaultTextEncodingName("gbk");
        // 屏幕自适应
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webSettings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        /**禁止屏幕自动旋转*/
        this.setScrollBarStyle(this.SCROLLBARS_INSIDE_OVERLAY);
        this.setHorizontalScrollBarEnabled(false);
        this.setHorizontalFadingEdgeEnabled(false);
        this.setVerticalFadingEdgeEnabled(false);


        HtmlMessageForLocal newWebViewActivity = new HtmlMessageForLocal();
        this.addJavascriptInterface(newWebViewActivity, "android");

        /**
         * 让网页的弹框转化为原生化的弹框
         */
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                parentLayout.removeView(view1);

                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("错误提醒")
                        .setMessage(message)
                        .setIcon(R.drawable.icon_login)
                        .setPositiveButton("好", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

        });
        setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 这些页面添加顶部导航
                if (url != null && !url.matches(".*"+ Constants.urlLogIn+".*")) {
                    Intent intent = new Intent(context, NewWebViewActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("animation",R.anim.slide_right_out);
                    context.startActivity(intent);
                    //((Activity)context).overridePendingTransition(R.anim.slide_right_in, R.anim.none);
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            /**
             * WebView开始加载
             * 1.通过截取网页 url 中指定的参数确定这个页面
             * 2.根据匹配结果而确定所要操作的是哪个页面
             * 3.注册广播接收器，接受消息处理类中发过来的广播去刷新数据
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                control.setTag("");

                int hhh = url.indexOf("WebViewRefreshNotification=");
                if (hhh != -1){
                    int xxx = hhh + 27;
                    sbFunName = url.substring(xxx);
                    int ttt = sbFunName.indexOf('&');
                    if (ttt!= -1){
                        sbFunName = sbFunName.substring(0,ttt);
                    }
                }

                /**
                 * 当最终截取到的字符串不是空的时候
                 * 再去注册广播来接受发过来的广播消息
                 */
                if (!sbFunName.equals("")){
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(sbFunName);
                    context.registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getAction().equals(sbFunName)) {  //接受广播
                                String name = intent.getStringExtra("name");
                                WebViewController.this.loadUrl("javascript:" + name);
                            }
                        }
                    }, intentFilter);
                }

                /**
                 * 动态创建网络加载中的GIf图片
                 */
                imageView = new ImageView(ActivityCollector.getTopActivity().getApplicationContext());
                ViewGroup.LayoutParams size = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
                imageView.setLayoutParams(size);
                Glide.with(ActivityCollector.getTopActivity()).load(R.drawable.loading3).into(imageView);
                parentLayout = (ViewGroup) WebViewController.this.getParent().getParent();
                parentLayout.addView(imageView);
                parentLayout.getPaddingRight();
                imageView.setVisibility(View.VISIBLE);

                /*AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(ActivityCollector.getTopActivity().getApplicationContext());
                avLoadingIndicatorView.setIndicator("BallPulseIndicator");
                avLoadingIndicatorView.setIndicatorColor(Color.WHITE);
                ViewGroup.LayoutParams sizes = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                avLoadingIndicatorView.setLayoutParams(sizes);
                ViewGroup parentLayouts = (ViewGroup) WebViewController.this.getParent();
                parentLayouts.addView(avLoadingIndicatorView);
                parentLayout.getPaddingStart();
                avLoadingIndicatorView.setVisibility(View.VISIBLE);*/
            }

            /**
             * WebView加载完成
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                imageView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 屏幕滚动监听
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        /*//Log.e(TAG, "1111111111111 "+l );  //等于0
        Log.e(TAG, "ttttttttttttt" + t);  //精确计算向下滚动距离，从列表没滑动时零开始
        *//*Log.e(TAG, "oldllllllllll"+oldl );*//*//等于0
        //Log.e(TAG, "oldtttttttttt"+oldt ); //手指滑动屏幕的的距离坐标，同上，坐标不好确定
        ImageView imageView = (ImageView) findViewById(R.id.topBackground);

        if (t >=1644 ){
            imageView.setAlpha(0.100f);
        }else if (t>=1680){
            imageView.setAlpha(0.500f);
        }else if (t>=1730){
            imageView.setAlpha(0.800f);
        }else if (t>=1800){
            imageView.setAlpha(0.999f);
        }
        super.onScrollChanged(l, t, oldl, oldt);*/

    }

    public void isShowLoading(Boolean is){
        if(parentLayout!=null){
            view1 = LayoutInflater.from(context).inflate(R.layout.loading_gif,null);
            if(is){
                //view1.setAlpha(0.5f);
                parentLayout.addView(view1);
            }else{
                parentLayout.removeView(view1);
            }
        }
    }

}
