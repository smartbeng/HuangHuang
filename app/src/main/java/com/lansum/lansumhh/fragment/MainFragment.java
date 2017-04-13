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
public class MainFragment extends Fragment {

    private View view;

    private WebView mainWebView;

    public  MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.lansum.lansumhh.fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        mainWebView = (WebView) view.findViewById(R.id.main_webview);
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.setWebViewClient(new WebViewClient());
        mainWebView.loadUrl(Constants.urlHostBase + Constants.urlIndex);
        return view;
    }

}
