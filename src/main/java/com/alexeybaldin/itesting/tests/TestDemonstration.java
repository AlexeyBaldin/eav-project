package com.alexeybaldin.itesting.tests;

import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;

@MyTesterTarget
public class TestDemonstration {

    @MyTest
    public static boolean colorSuccessTest() {
        return true;
    }

    @MyTest
    public static boolean colorErrorTest() {
        return false;
    }
}
