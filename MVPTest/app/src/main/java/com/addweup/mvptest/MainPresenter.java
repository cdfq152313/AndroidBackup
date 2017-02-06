package com.addweup.mvptest;

import java.util.Random;

/**
 * Created by cdfq1 on 2017/1/2.
 */

public class MainPresenter implements MainContract.MainPresenter{
    MainContract.MainView mainView;
    Random random;
    public MainPresenter(MainContract.MainView view, Random random){
        mainView = view;
        this.random = random;
    }

    @Override
    public void random() {
        if ( random.nextBoolean() ){
            mainView.showError();
        }
        else{
            mainView.showNormal();
        }
    }

    public void readData(Calculator.Listener listener){
        Calculator calculator = new Calculator();
        calculator.calculate(listener);
    }

}
