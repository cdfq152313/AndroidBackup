package com.addweup.mytabtry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragmentTryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_try);
        getFragmentManager();
    }
}
