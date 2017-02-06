package com.addweup.lifecycleapp;

import org.junit.Test;

import static org.junit.Assert.*;
import java.lang.reflect.Method;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void correct() throws Exception {
        String methodName = "getFruitName";
        Object apple = new Apple();
        Method method = apple.getClass().getMethod(methodName);
        String x = (String) method.invoke(apple);
        assertEquals(x, "Apple");
    }

    @Test
    public void error() throws Exception {
        String methodName = "getFruitName";
        Object apple = new Apple();
        Method method = apple.getClass().getMethod(methodName);
        String x = (String) method.invoke(apple);
        assertEquals(x, "Banana");
    }

    private class Apple{
        public String getFruitName(){
            return "Apple";
        }
    }
}