package com.alexeybaldin.itesting.tests;

import com.alexeybaldin.itesting.annotations.MyClearMethod;
import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;

import static com.alexeybaldin.constant.Color.ANSI_BLUE;

@MyTesterTarget
public class TestDemonstration {

    @MyClearMethod
    public static void clear() {
        System.out.println(ANSI_BLUE + "DEMO clear");
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
