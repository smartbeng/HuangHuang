package com.lansum.eip.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lansum.eip.R;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.activity.mainfragment.DoorActivity;
import com.lansum.eip.util.ActivityCollector;
import com.lansum.eip.util.ToastStudio;


/**
 * Created by MaiBenBen on 2017/4/14.
 */

public class WebViewController extends WebView{
    private Context context;

    private WebViewController control;
    //网络未加载完的loading图片
    private ImageView imageView;

    public WebViewController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =  context;
        webviewSettings();
    }

    public WebViewController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        webviewSettings();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebViewController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        webviewSettings();
    }

    public WebViewController(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
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

        this.setScrollBarStyle(this.SCROLLBARS_INSIDE_OVERLAY);

        this.setHorizontalScrollBarEnabled(false);
        this.setHorizontalFadingEdgeEnabled(false);
        this.setVerticalFadingEdgeEnabled(false);


        HtmlMessageForLocal newWebViewActivity = new HtmlMessageForLocal();

        this.addJavascriptInterface(newWebViewActivity,"android");

        /**
         * 让网页的弹框转化为原生化的弹框
         */
        MyWebChromeClient client = new MyWebChromeClient(context){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                //
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("标题").setMessage(message)
                        .setPositiveButton("ok", new AlertDialog.OnClickListener() {
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
        };
        setWebChromeClient(client);
        setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 这些页面添加顶部导航
                if (url != null) {
                    /*Intent intent = new Intent(context, NewWebViewActivity.class);
                    intent.putExtra("url", url);
                    context.startActivity(intent);*/
                } else {
                    return false;
                }
                return false;

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            /**
             * webview加载完成
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                control.setTag("");
                imageView = new ImageView(ActivityCollector.getTopActivity().getApplicationContext());
                ViewGroup.LayoutParams size = new ViewGroup.LayoutParams(100,100);
                imageView.setLayoutParams(size);
                Glide.with(ActivityCollector.getTopActivity()).load(R.drawable.loading3).into(imageView);
                ViewGroup parentLayout =(ViewGroup)WebViewController.this.getParent();
                parentLayout.addView(imageView);
            }

        });
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }
}
