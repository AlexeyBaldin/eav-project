package com.alexeybaldin.eav;

import com.alexeybaldin.test.EAVTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.util.Objects;

@SpringBootApplication
public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class);
    }
}


@SpringBootApplication
class SpringBootTestStarter {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootTestStarter.class);
        EAVTest.runTests();
        context.close();
    }
}


