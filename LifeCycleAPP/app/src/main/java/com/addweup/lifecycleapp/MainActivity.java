package com.addweup.lifecycleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected String getTAG(){
        return "Main";
    }

    public void secondClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, SecondAcitivity.class);
        startActivity(intent);
    }

    public void updateTextClick(View view){
        TextView textView = (TextView) findViewById(R.id.display);
        EditText editText = (EditText) findViewById(R.id.editText);

        String text = editText.getText().toString();
        textView.setText(text);
    }
}
