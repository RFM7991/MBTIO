package com.rk.mbtio.CreateUserFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rk.mbtio.R;

public class Q2Fragment extends Fragment {

    public Q2Fragment() {
        // required empty
    }

    public static Q2Fragment newInstance() {
        Q2Fragment fragment = new Q2Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // work with args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q2, container, false);
    }
}
