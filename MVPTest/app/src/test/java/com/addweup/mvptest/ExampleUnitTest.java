package com.addweup.mvptest;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MainContract.MainView view;

    @Mock
    Random random;

    MainPresenter presenter;

    @Before
    public void before(){
        presenter = new MainPresenter(view, random);
    }

    @Test
    public void testApple(){
        String a = "apple";
        String b = "apple";
        assertEquals(a,b);
    }

    @Test
    public void testTrue(){
        Mockito.when( random.nextBoolean() ).thenReturn(true);
        presenter.random();
        Mockito.verify(view).showError();
    }

    @Test
    public void testFalse(){
        Mockito.when( random.nextBoolean() ).thenReturn(false);
        presenter.random();
        Mockito.verify(view).showNormal();
    }

    boolean success;
    @Test
    public void testCallback() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        success = false;
        Calculator.Listener listener = new Calculator.Listener() {
            @Override
            public void callback() {
                success = true;
                latch.countDown();
            }
        };
        Calculator calculator = new Calculator();
        calculator.calculate(listener);

        latch.await(2, TimeUnit.SECONDS);
        if(!success){
            fail("You cannot pass");
        }
    }
}