package com.alexeybaldin.eav;

import com.alexeybaldin.itesting.EAVTest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
class Tester implements ApplicationRunner {
    public void run(ApplicationArguments args) {
        EAVTest.runTests();
    }
}





