package com.alexeybaldin.itesting;

import java.lang.reflect.Method;
import java.util.HashSet;

public class MyTestInformation {
    private Class<?> testingClass;
    private Method testingMethod;
    private boolean result;
    private double timeInSeconds;
    private HashSet<String> errors;

    public MyTestInformation(Class<?> testingClass, Method testingMethod, boolean result, double timeInSeconds) {
        this.testingClass = testingClass;
        this.testingMethod = testingMethod;
        this.result = result;
        this.timeInSeconds = timeInSeconds;
        this.errors = new HashSet<>();
    }

    public Class<?> getTestingClass() {
        return testingClass;
    }

    public void setTestingClass(Class<?> testingClass) {
        this.testingClass = testingClass;
    }

    public Method getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(Method testingMethod) {
        this.testingMethod = testingMethod;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(double timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public HashSet<String> getErrors() {
        return errors;
    }

    public void setErrors(HashSet<String> errors) {
        this.errors = errors;
    }
}
