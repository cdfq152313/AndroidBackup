package com.addweup.mytabtry.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.addweup.mytabtry.base.BaseDialogFragment;

/**
 * Created by cdfq1 on 2017/3/9.
 */

public class MyDialogFragment extends BaseDialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hello")
                .setPositiveButton("ok", null);
        return builder.create();
    }
}
