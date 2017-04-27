package com.lansum.eip.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lansum.eip.R;
import com.lansum.eip.activity.MainActivity;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.util.ActivityCollector;

/**
 * A simple {@link Fragment} subclass.
 */
public class QingJiaFragment extends Fragment {

    //请假的 WebView 页面
    private WebView QingJiaWebView;

    public QingJiaFragment() {
        // Required empty public constructor
    }

    /**
     * 初始化加载布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*View view =  inflater.inflate(R.layout.fragment_qing_jia, container, false);
        QingJiaWebView = (WebView) view.findViewById(R.id.qingjia_webview);
        QingJiaWebView.loadUrl(Constants.urlHostBase + Constants.urlQingJia);
        return view;*/
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startFragment();
            }
        });*/
        return null;
    }
    /*public void startFragment(){
        Intent intent = new Intent(, NewWebViewActivity.class);
        intent.putExtra("url", Constants.urlHostBase + Constants.urlQingJia);
        intent.putExtra("animation",R.anim.slide_right_out);
        startActivity(intent);
        //从右往左进入
        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.none);
    }*/

}
