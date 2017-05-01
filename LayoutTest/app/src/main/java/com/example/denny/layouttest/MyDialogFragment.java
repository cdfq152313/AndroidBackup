package com.example.denny.layouttest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by denny on 2017/4/18.
 */

public class MyDialogFragment extends DialogFragment {
    Button b1;
    Button b2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.dialog_view, null, false);
        View header = inflater.inflate(R.layout.dialog_header, null, false);
        View body = inflater.inflate(R.layout.dialog_body, null, false);
        View button = inflater.inflate(R.layout.dialog_button, null, false);
        b1 = (Button) button.findViewById(R.id.b1);
        b2 = (Button) button.findViewById(R.id.b2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        root.addView(header);
        root.addView(body);
        root.addView(button);

        builder.setView(root);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
