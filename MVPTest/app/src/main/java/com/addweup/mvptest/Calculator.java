package com.addweup.mvptest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by cdfq1 on 2017/1/2.
 */

public class Calculator {
    Executor executor = Executors.newSingleThreadExecutor();

    public void calculate(final Listener listener){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    listener.callback();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface Listener{
        void callback();
    }
}
