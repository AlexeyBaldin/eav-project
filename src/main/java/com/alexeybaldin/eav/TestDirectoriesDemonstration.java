package com.alexeybaldin.eav;

import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;

@MyTesterTarget
public class TestDirectoriesDemonstration {
    @MyTest
    public static boolean a() {
        return true;
    }
}
