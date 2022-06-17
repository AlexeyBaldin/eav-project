package com.alexeybaldin.itesting;

public class MyTestingFramework {

    private MyTestingFramework() {}

    public static void run(String... testDirectories) throws Exception {
        if (testDirectories.length < 1) {
            throw new Exception("No directories to tests. Please choose one or few with test classes.");
        }

        MyTester.setup(testDirectories);

        if(MyTester.getClassesCount() < 1) {
            throw new Exception("No classes to tests. Please check your test directories.");
        }
        if(MyTester.getErrorClassesString().length() != 0) {
            throw new Exception("Please check following classes for error: " + MyTester.getErrorClassesString());
        }

        MyTestPrinter.hello();

        MyTester.run(testDirectories);

        MyTestPrinter.printTestResults(MyTester.getTestResults());
        MyTestPrinter.goodbye();
    }
}
