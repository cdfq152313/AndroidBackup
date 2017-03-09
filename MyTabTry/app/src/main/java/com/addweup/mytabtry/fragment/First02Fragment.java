package com.addweup.mytabtry.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addweup.mytabtry.R;
import com.addweup.mytabtry.base.BaseFragment;

public class First02Fragment extends BaseFragment {

    public First02Fragment() {
        // Required empty public constructor
    }

    public static First02Fragment newInstance() {
        First02Fragment fragment = new First02Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first02, container, false);
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
