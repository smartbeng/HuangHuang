package com.lansum.eip.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lansum.eip.R;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.activity.homefragment.KaoQinActivity;
import com.lansum.eip.activity.homefragment.UserSettings;
import com.lansum.eip.activity.homefragment.UserTeam;
import com.lansum.eip.activity.homefragment.UserDataActivity;
import com.lansum.eip.activity.mainfragment.NoticeActivity;
import com.lansum.eip.http.Constants;
import com.lansum.eip.view.CommItemView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private CommItemView dataView;      //资料项

    private CommItemView userKaoQin;    //考勤项

    private CommItemView userTeam;      //团队

    private CommItemView serviceNotice; //系统通知

    private CommItemView userSettings;  //用户设置

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        dataView = (CommItemView) view.findViewById(R.id.item_data);
        userKaoQin = (CommItemView) view.findViewById(R.id.item_kaoqin);
        userTeam = (CommItemView) view.findViewById(R.id.item_team);
        serviceNotice = (CommItemView) view.findViewById(R.id.item_nation);
        userSettings = (CommItemView) view.findViewById(R.id.item_setting);
        dataView.setOnClickListener(this);
        userKaoQin.setOnClickListener(this);
        userTeam.setOnClickListener(this);
        serviceNotice.setOnClickListener(this);
        userSettings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_data:   //用户资料
                startActivity(new Intent(getActivity(), UserDataActivity.class));
                break;
            case R.id.item_kaoqin: //用户考勤
                Intent intent = new Intent(getActivity(), NewWebViewActivity.class);
                intent.putExtra("url", Constants.urlHostBase + Constants.urlAttendance);
                intent.putExtra("animation",R.anim.slide_right_out);
                startActivity(intent);
                //从右往左进入
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.none);
/*                startActivity(new Intent(getActivity(), KaoQinActivity.class));*/
                break;
            case R.id.item_team:   //用户团队
                startActivity(new Intent(getActivity(), UserTeam.class));
                break;
            case R.id.item_nation: //系统通知
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;
            case R.id.item_setting://用户设置
                startActivity(new Intent(getActivity(), UserSettings.class));
                break;
        }
    }
}
