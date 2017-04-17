package com.lansum.lansumhh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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

    private Toolbar toolbar;

    public QingJiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_qing_jia, container, false);
        QingJiaWebView = (WebView) view.findViewById(R.id.qingjia_webview);

        //toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);

        QingJiaWebView.loadUrl(Constants.urlHostBase + Constants.urlQingJia);
        return view;
    }

}
