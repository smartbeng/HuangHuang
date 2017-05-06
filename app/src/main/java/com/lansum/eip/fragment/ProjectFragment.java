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
public class ProjectFragment extends Fragment {

    private WebViewController projectWebView;

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_web_view, container, false);
        projectWebView = (WebViewController) view.findViewById(R.id.base_web_view);
        projectWebView.loadUrl(Constants.urlHostHigh + Constants.urlXiangMuGuanLi);
        return view;
    }

}
