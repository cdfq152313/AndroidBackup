package com.addweup.mytabtry.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addweup.mytabtry.R;

public class FirstFragment extends Fragment {
    static final String TAG = "FirstFragment";
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mParam;

    public FirstFragment() {
        // Required empty public constructor
    }

    int onCreateCount = 0;
    int onCreateViewCount = 0;

    public static FirstFragment newInstance(String param1) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, String.format("onCreate count: %d", ++onCreateCount));
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, String.format("onCreateView count: %d", ++onCreateViewCount));
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
