package com.addweup.mytabtry;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.addweup.mytabtry.fragment.FirstFragment;
import com.addweup.mytabtry.fragment.SecondFragment;

public class FragmentTryActivity extends AppCompatActivity {

    FirstFragment firstFragment;
    SecondFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_try);
        if(savedInstanceState==null){
            setDefaultFragment();
        }
    }

    private void setDefaultFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        firstFragment = FirstFragment.newInstance("Hello World");
        transaction.replace(R.id.myfragment, firstFragment);
        transaction.commit();
    }

    public void showFirstFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(firstFragment == null){
            firstFragment = FirstFragment.newInstance("Hello World");
        }
        transaction.replace(R.id.myfragment, firstFragment);
        transaction.commit();
    }

    public void showSecondFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(secondFragment == null){
            secondFragment = SecondFragment.newInstance();
        }
        transaction.replace(R.id.myfragment, secondFragment);
        transaction.commit();
    }

    public void showThirdActivity(){
        Intent intent = new Intent();
        intent.setClass(this, ThirdActivity.class);
        startActivity(intent);
    }
}
