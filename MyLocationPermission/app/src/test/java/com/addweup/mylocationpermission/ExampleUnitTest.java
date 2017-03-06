package com.addweup.mylocationpermission;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        C1 c1 = new C1();
        System.out.println(c1.getMyClass());
        C2 c2 = new C2();
        System.out.println(c2.getMyClass());
    }
}

class C1{
    public String getMyClass(){
        return C1.this.getClass().getName();
    }
}

class C2 extends C1{

}