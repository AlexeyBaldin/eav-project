package com.alexeybaldin.eav;

import com.alexeybaldin.itesting.*;
import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class);
    }
}


@Component
class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        try {
            MyTestingFramework.run("com.alexeybaldin.itesting.tests", "com.alexeybaldin.eav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}











