package com.addweup.mvptest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainContract.MainView{

    MainContract.MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, new Random());
    }

    public void click(View view){
        presenter.random();
    }

    public void nomvpClick(){
        Random r = new Random();
        if (r.nextBoolean() ){
            showNormal();
        }
        else{
            showError();
        }
    }

    @Override
    public void showError() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("ERROR");
        b.setNegativeButton("ok", null);
        b.show();
    }

    @Override
    public void showNormal() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Normal");
        b.setNegativeButton("ok", null);
        b.show();
    }
}
