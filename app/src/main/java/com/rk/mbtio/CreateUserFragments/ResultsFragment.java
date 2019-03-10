package com.rk.mbtio.CreateUserFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rk.mbtio.R;

public class ResultsFragment extends Fragment {

    private String MBTI = "";

    public ResultsFragment() {
        // required empty
    }

    public static ResultsFragment newInstance(String mbti) {
        ResultsFragment fragment = new ResultsFragment();

        Bundle args = new Bundle();
        args.putString("mbti",mbti);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MBTI =  getArguments().get("mbti").toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results, container, false);


        if (getArguments() != null) {
            MBTI =  getArguments().get("mbti").toString();
        }

        TextView results = v.findViewById(R.id.results);

        results.setText(MBTI);

        return v;
    }
}
