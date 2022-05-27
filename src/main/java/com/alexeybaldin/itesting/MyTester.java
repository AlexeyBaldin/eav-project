package com.alexeybaldin.itesting;


import com.alexeybaldin.itesting.annotations.*;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alexeybaldin.constant.Color.*;

@Component
public class MyTester {

    private static Set<Class<?>> CLASSES;

    private static HashSet<MyTestInformation> testResults;

    private static StringBuilder errorClasses;

    public static void run(String... testDirectories) {

        setup(testDirectories);

        if (testDirectories.length < 1) {
            System.out.println("Empty directories. Please choose one or few with test classes");
        } else {
            if (errorClasses.length() == 0 && CLASSES.size() > 0) {
                System.out.println("=========================================================================START TESTING=========================================================================");

                CLASSES.forEach(MyTester::testClass);

                System.out.println("============================================================================RESULTS============================================================================");

                System.out.println(getTestResults());

                System.out.println("========================================================================FINISH TESTING=========================================================================");

            } else {
                System.out.println(ANSI_RED + "Please check following classes for errors: " + errorClasses + ANSI_RESET);
            }
        }

    }

    private static void setup(String... testDirectories) {
        CLASSES = new HashSet<>();
        testResults = new HashSet<>();
        errorClasses = new StringBuilder();


        for (String directory : testDirectories) {
            Reflections reflections = new Reflections(directory);
            CLASSES.addAll(reflections.getTypesAnnotatedWith(MyTesterTarget.class));
            CLASSES.forEach(System.out::println);
        }

        checkBeforeAndAfterAnnotationsAndFindDefectiveClasses();
    }

    private static void checkBeforeAndAfterAnnotationsAndFindDefectiveClasses() {

        CLASSES.forEach(testClass -> {
            Set<Method> methods = new HashSet<>();
            Collections.addAll(methods, testClass.getMethods());
            AtomicInteger beforeTestCount = new AtomicInteger();
            AtomicInteger afterTestCount = new AtomicInteger();
            AtomicInteger beforeAllTestsCount = new AtomicInteger();
            AtomicInteger afterAllTestsCount = new AtomicInteger();
            methods.forEach(method -> {
                if (method.isAnnotationPresent(MyBeforeTest.class)) {
                    beforeTestCount.getAndIncrement();
                }
                if (method.isAnnotationPresent(MyAfterTest.class)) {
                    afterTestCount.getAndIncrement();
                }
                if (method.isAnnotationPresent(MyBeforeAllTests.class)) {
                    beforeAllTestsCount.getAndIncrement();
                }
                if (method.isAnnotationPresent(MyAfterAllTests.class)) {
                    afterAllTestsCount.getAndIncrement();
                }
            });
            if (beforeTestCount.get() > 1 || afterTestCount.get() > 1 ||
                    beforeAllTestsCount.get() > 1 || afterAllTestsCount.get() > 1) {
                errorClasses.append(testClass.getName()).append("   ");
            }
        });
    }

    private static void testClass(Class<?> testedClass) {
        Set<Method> tests = findTests(testedClass);
        Method beforeAllTestsMethod = findBeforeAllTestsMethod(testedClass);
        Method afterAllTestsMethod = findAfterAllTestsMethod(testedClass);
        Method beforeTestMethod = findBeforeTestMethod(testedClass);
        Method afterTestMethod = findAfterTestMethod(testedClass);

        if(beforeAllTestsMethod != null) {
            try {
                beforeAllTestsMethod.invoke(testedClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        tests.forEach(method -> testMethod(testedClass, method, beforeTestMethod, afterTestMethod));

        if(afterAllTestsMethod != null) {
            try {
                afterAllTestsMethod.invoke(testedClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static Method findBeforeTestMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyBeforeTest.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findAfterTestMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyAfterTest.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findBeforeAllTestsMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyBeforeAllTests.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findAfterAllTestsMethod(Class<?> testedClass) {
        for (Method method : testedClass.getMethods()) {
            if (method.isAnnotationPresent(MyAfterAllTests.class)) {
                return method;
            }
        }
        return null;
    }

    private static Set<Method> findTests(Class<?> testedClass) {
        Set<Method> methods = new HashSet<>();
        Set<Method> tests = new HashSet<>();
        Collections.addAll(methods, testedClass.getMethods());

        methods.forEach(method -> {
            if (method.isAnnotationPresent(MyTest.class) && method.getReturnType() == boolean.class) {
                tests.add(method);
            }
        });
        return tests;
    }

    private static void testMethod(Class<?> testedClass, Method testedMethod,
                                   Method beforeTestMethod, Method afterTestMethod) {
        if (beforeTestMethod != null) {
            try {
                beforeTestMethod.invoke(testedClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long time = System.currentTimeMillis();
        boolean success = false;
        try {
            success = (boolean) testedMethod.invoke(testedClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;

        if (afterTestMethod != null) {
            try {
                afterTestMethod.invoke(testedClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        testResults.add(new MyTestInformation(testedClass, testedMethod, success, (double)time / 1000));
    }

    private static StringBuilder getTestResults() {
        StringBuilder stringBuilder = new StringBuilder();

        testResults.forEach(testResult -> {
            stringBuilder.append("[" + ANSI_CYAN).append(testResult.getTestingClass().getName()).append(ANSI_BLUE).append("   ").append(testResult.getTestingMethod().getName()).append(ANSI_RESET).append("]");

            if (testResult.getResult()) {
                stringBuilder.append(ANSI_GREEN + "   Test Success    " + ANSI_YELLOW).append(testResult.getTimeInSeconds()).append(" sec").append(ANSI_RESET).append('\n');
            } else {
                stringBuilder.append(ANSI_RED + "    Test Error    " + ANSI_YELLOW).append(testResult.getTimeInSeconds()).append(" sec").append(ANSI_RESET).append('\n');
            }
        });

        return stringBuilder;
    }
}
