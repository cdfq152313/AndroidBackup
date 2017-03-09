package com.addweup.mytabtry.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.addweup.mytabtry.R;
import com.addweup.mytabtry.base.BaseFragment;

public class FirstFragment extends BaseFragment implements View.OnClickListener{
    static final String TAG = "FirstFragment";
    private static final String ARG_PARAM = "param";

    View view;
    TextView textView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, String.format("onCreateView count: %d", ++onCreateViewCount));
        if(view == null){
            view = inflater.inflate(R.layout.fragment_first, container, false);
            view.findViewById(R.id.first02).setOnClickListener(this);
            textView = (TextView)view.findViewById(R.id.textView);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", textView.getText().toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            String text = savedInstanceState.getString("key", "No Save Data QQ");
            textView.setText(text);
            Log.i(TAG, text);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.first02){
            showFirst02();
        }
    }

    private void showFirst02(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        textView.setText("GOGOGO first01 fragment");
        transaction.replace(R.id.myfragment, First02Fragment.newInstance());
        transaction.commit();
    }
}
