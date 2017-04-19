package com.lansum.eip.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lansum.eip.R;
import com.lansum.eip.activity.DoorActivity;
import com.lansum.eip.activity.NoticeActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.webview.WebViewController;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private View view;

    private WebViewController mainWebView;


     //通知图标
    //@BindView(R.id.tongZhiImage)
    private ImageView tongImageView;

    //开门图标
    //@BindView(R.id.open_door)
    private ImageView doorImageView;

    public  MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.lansum.lansumhh.fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        mainWebView = (WebViewController) view.findViewById(R.id.main_webview);
        mainWebView.loadUrl(Constants.urlHostBase + Constants.urlIndex);

        tongImageView = (ImageView) view.findViewById(R.id.tongZhiImage);
        doorImageView = (ImageView) view.findViewById(R.id.open_door);
        tongImageView.setOnClickListener(this);
        doorImageView.setOnClickListener(this);
        return view;
    }

    /**
     * 主页导航栏点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tongZhiImage:
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;
            case R.id.open_door:
                Intent intent = new Intent(getActivity(),DoorActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}