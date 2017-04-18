package com.lansum.lansumhh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lansum.lansumhh.R;
import com.lansum.lansumhh.activity.MainActivity;
import com.lansum.lansumhh.http.Constants;
import com.lansum.lansumhh.util.ToastStudio;
import com.lansum.lansumhh.webview.WebViewController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                ToastStudio.showToast(getContext().getApplicationContext(),"通知");
                break;
            case R.id.open_door:
                ToastStudio.showToast(getContext().getApplicationContext(),"开门");
                break;
            default:
                break;
        }
    }

}
