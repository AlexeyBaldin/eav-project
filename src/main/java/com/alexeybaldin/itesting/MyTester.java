package com.alexeybaldin.itesting;


import com.alexeybaldin.itesting.annotations.MyClearMethod;
import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alexeybaldin.constant.Color.*;

public class MyTester {

    private final static Set<Class<?>> CLASSES;

    private static HashSet<MyTestInformation> testResults;

    private static StringBuilder errorClasses;

    static {
        Reflections reflections = new Reflections("com.alexeybaldin.itesting.tests");
        CLASSES = reflections.getTypesAnnotatedWith(MyTesterTarget.class);
        testResults = new HashSet<>();
        errorClasses = new StringBuilder();
        checkClearAnnotationAndFindDefectiveClassNames();
    }



    public static void run() {

        if(errorClasses.length() == 0) {
            System.out.println("=========================================================================START TESTING=========================================================================");

            CLASSES.forEach(MyTester::testClass);

            System.out.println("============================================================================RESULTS============================================================================");

            System.out.println(getTestResults());

            System.out.println("========================================================================FINISH TESTING=========================================================================");

        } else {
            System.out.println(ANSI_RED + "Please check following classes for errors: " + errorClasses + ANSI_RESET);
        }
    }

    private static void checkClearAnnotationAndFindDefectiveClassNames() {

        CLASSES.forEach(testClass -> {
            Set<Method> methods = new HashSet<>();
            Collections.addAll(methods, testClass.getMethods());
            AtomicInteger clearClassCount = new AtomicInteger();
            methods.forEach(method -> {
                if(method.isAnnotationPresent(MyClearMethod.class)) {
                    clearClassCount.getAndIncrement();
                }
            });
            if(clearClassCount.get() > 1) {
                errorClasses.append(testClass.getName()).append("   ");
            }
        });
    }

    private static void testClass(Class<?> testedClass) {
        Set<Method> methods = new HashSet<>();
        Collections.addAll(methods, testedClass.getMethods());
        Method clearMethod = findClearMethod(methods);

        methods.forEach(method -> testMethod(testedClass, method, clearMethod));

    }

    private static Method findClearMethod( Set<Method> methods) {
        for(Method method : methods) {
            if(method.isAnnotationPresent(MyClearMethod.class)) {
                return method;
            }
        }
        return null;
    }

    private static void testMethod(Class<?> testedClass, Method testedMethod, Method clearMethod) {
        if(testedMethod.isAnnotationPresent(MyTest.class) && testedMethod.getReturnType() == boolean.class) {
            long time = System.currentTimeMillis();
            boolean success = false;
            try {
                success = (boolean)testedMethod.invoke(testedClass);
                if(clearMethod != null) {
                    clearMethod.invoke(testedClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            testResults.add(new MyTestInformation(testedClass, testedMethod, success, (double)(System.currentTimeMillis() - time) / 1000));
        }
    }

    private static StringBuilder getTestResults() {
        StringBuilder stringBuilder = new StringBuilder();

        testResults.forEach(testResult -> {
            stringBuilder.append("[" + ANSI_CYAN).append(testResult.getTestingClass().getName()).append(ANSI_BLUE).append("   ").append(testResult.getTestingMethod().getName()).append(ANSI_RESET).append("]");

            if(testResult.getResult()) {
                stringBuilder.append(ANSI_GREEN + "   Test Success    " + ANSI_YELLOW).append(testResult.getTimeInSeconds()).append(" sec").append(ANSI_RESET).append('\n');
            } else {
                stringBuilder.append(ANSI_RED + "    Test Error    " + ANSI_YELLOW).append(testResult.getTimeInSeconds()).append(" sec").append(ANSI_RESET).append('\n');
            }
        });

        return stringBuilder;
    }
}
