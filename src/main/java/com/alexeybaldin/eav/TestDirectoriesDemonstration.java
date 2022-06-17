package com.alexeybaldin.eav;

import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;

@MyTesterTarget
public class TestDirectoriesDemonstration {
    @MyTest
    public static boolean waitTest() throws InterruptedException {
        Thread.sleep(1234, 56789);
        return true;
    }
}
