package com.lansum.eip.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lansum.eip.R;
import com.lansum.eip.activity.NewWebViewActivity;
import com.lansum.eip.http.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {


    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        return view;
    }
}
