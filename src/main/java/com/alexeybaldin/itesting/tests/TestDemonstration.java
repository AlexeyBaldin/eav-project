package com.alexeybaldin.itesting.tests;

import com.alexeybaldin.itesting.annotations.*;

import static com.alexeybaldin.constant.Color.*;

@MyTesterTarget
public class TestDemonstration {

    @MyAfterAllTests
    public static void clear() {
        System.out.println(ANSI_BLUE + "DEMO AFTER ALL TESTS" + ANSI_RESET);
    }

    @MyTest
    public static boolean colorSuccessTest() {
        return true;
    }

    @MyTest
    public static boolean colorErrorTest() {
        return false;
    }
}
