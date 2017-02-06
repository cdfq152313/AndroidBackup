package com.addweup.mvptest;

/**
 * Created by cdfq1 on 2017/1/2.
 */

public class MainContract {
    public interface MainPresenter{
        void random();
    }

    public interface  MainView{
        void showError();
        void showNormal();
    }
}
