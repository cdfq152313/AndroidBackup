package com.addweup.myalertdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by cdfq1 on 2017/3/8.
 */

public class MyDialogFrament extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hello2")
                .setMessage("World2")
                .setPositiveButton("YA", null)
                .setNegativeButton("NO", null);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positive.setTextColor(getResources().getColor(R.color.red));
        negative.setTextColor(getResources().getColor(R.color.blue));
    }
}
