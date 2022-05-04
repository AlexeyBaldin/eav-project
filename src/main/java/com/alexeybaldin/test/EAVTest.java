package com.alexeybaldin.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import static com.alexeybaldin.constant.Color.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyTest {
}

public class EAVTest {

    private static final ArrayList<Method> methods;

    static {
        methods = new ArrayList<>();
        Collections.addAll(methods, EAVTest.class.getMethods());
    }

    public static void runTests() {
        Class<EAVTest> eavTestClass = EAVTest.class;
        methods.forEach(method -> {
            if(method.isAnnotationPresent(MyTest.class) && method.getReturnType() == boolean.class) {
                System.out.print(ANSI_CYAN + "[" + method.getDeclaringClass().getName() + ", " + method.getName() + "]" + ANSI_RESET);
                try {
                    boolean success = (boolean)method.invoke(eavTestClass);
                    if(success) {
                        System.out.println(ANSI_GREEN + "   Test Success    " + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + "    Test Error    " + ANSI_RESET);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @MyTest
    public static boolean testSuccessTest() {
        System.out.print(ANSI_YELLOW + " TESTING COLORS DEMONSTRATION");
        return true;
    }

    @MyTest
    public static boolean testFailedTest() {
        System.out.print(ANSI_YELLOW + " TESTING COLORS DEMONSTRATION");
        return false;
    }
}
