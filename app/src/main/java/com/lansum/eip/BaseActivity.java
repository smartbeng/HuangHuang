package com.lansum.eip;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by MaiBenBen on 2017/4/25.
 */
public class BaseActivity extends AppCompatActivity {

    public BaseActivity baseActivity;

    // 主容器
    LinearLayout mMainContainer;


    // 滑动容器
    SlidingPaneLayout mSlidingPaneLayout;
    private WebView mWebView;

    ProgressDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_new_web_view);

        mWebView = (WebView) findViewById(R.id.base_web_view);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在加载！");
        // 设置加载网页的地址
        mWebView.loadUrl("http://www.baidu.com");

        // 解决跳转到第三方浏览器
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

            // 页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!mDialog.isShowing())
                    mDialog.show();
            }

            /**
             * 页面加载完成
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mDialog.isShowing()){
                    mDialog.dismiss();
                    Toast.makeText(baseActivity, "夹杂", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 设置显示的标题
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // 设置 actionbar 显示的标题
                setTitle(title);
            }
        });
    }
}
