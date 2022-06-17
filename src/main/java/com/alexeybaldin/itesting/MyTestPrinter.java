package com.alexeybaldin.itesting;

import java.util.HashSet;
import java.util.Set;

import static com.alexeybaldin.constant.Color.*;

public class MyTestPrinter {

    private MyTestPrinter() {}

    static void hello() {
        System.out.println("=========================================================================START TESTING=========================================================================");
    }

    static void goodbye() {
        System.out.println("========================================================================FINISH TESTING=========================================================================");
    }

    static void printTestResults(HashSet<MyTestInformation> testResults) {
        System.out.println("============================================================================RESULTS============================================================================");
        System.out.println(formTestResultsString(testResults));
    }

    private static StringBuilder formTestResultsString(HashSet<MyTestInformation> testResults) {
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
