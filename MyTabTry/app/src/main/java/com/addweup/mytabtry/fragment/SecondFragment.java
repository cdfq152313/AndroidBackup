package com.addweup.mytabtry.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addweup.mytabtry.R;
import com.addweup.mytabtry.base.BaseFragment;

public class SecondFragment extends BaseFragment {
    static final String TAG = "SecondFragment";
    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    int onCreateCount = 0;
    int onCreateViewCount = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, String.format("onCreate count: %d", ++onCreateCount));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, String.format("onCreate count: %d", ++onCreateViewCount));

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        View button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyDialogFragment().show(getFragmentManager(), null);
            }
        });
        return view;
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
