package com.lansum.eip.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lansum.eip.R;
import com.lansum.eip.http.Constants;
import com.lansum.eip.webview.WebViewController;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkFragment extends Fragment {

    private WebViewController workWebView;

    public WorkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_new_web_view, container, false);
        workWebView = (WebViewController) view.findViewById(R.id.base_web_view);
        workWebView.loadUrl(Constants.urlHostHigh + Constants.urlAttendance);
        return view;
    }

}
