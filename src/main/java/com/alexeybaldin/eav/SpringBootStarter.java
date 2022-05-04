package com.alexeybaldin.eav;

import com.alexeybaldin.test.EAVTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarter {


    public static void main(String[] args) throws Exception {

        SpringApplication.run(SpringBootStarter.class);

        runTests();


    }

    public static void runTests() {
        System.out.println("============================================================================TESTING============================================================================");
        EAVTest.runTests();
        System.out.println("============================================================================TESTING============================================================================");
    }
}
