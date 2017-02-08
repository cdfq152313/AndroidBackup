package com.addweup.mytabtry.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.addweup.mytabtry.FragmentTryActivity;
import com.addweup.mytabtry.R;
import com.addweup.mytabtry.ThirdActivity;


public class BottomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomFragment() {
        // Required empty public constructor
    }

    Button firstButton;
    Button secondButton;
    Button thirdButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomFragment newInstance(String param1, String param2) {
        BottomFragment fragment = new BottomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bottom, container, false);
        firstButton = (Button) root.findViewById(R.id.firstButton);
        secondButton = (Button) root.findViewById(R.id.secondButton);
        thirdButton = (Button) root.findViewById(R.id.thirdButton);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTryActivity activity = (FragmentTryActivity) getActivity();
                activity.showFirstFragment();
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTryActivity activity = (FragmentTryActivity) getActivity();
                activity.showSecondFragment();
            }
        });
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTryActivity activity = (FragmentTryActivity) getActivity();
                activity.showThirdActivity();
            }
        });
        return root;
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
