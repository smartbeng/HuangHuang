package com.lansum.lansumhh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lansum.lansumhh.R;
import com.lansum.lansumhh.http.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class QingJiaFragment extends Fragment {

    private WebView QingJiaWebView;

    public QingJiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_qing_jia, container, false);
        QingJiaWebView = (WebView) view.findViewById(R.id.qingjia_webview);
        QingJiaWebView.getSettings().setJavaScriptEnabled(true);
        QingJiaWebView.setWebViewClient(new WebViewClient());
        QingJiaWebView.loadUrl(Constants.urlHostBase + Constants.urlQingJia);
        return view;
    }

}
