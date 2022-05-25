package com.alexeybaldin.eav;

import com.alexeybaldin.itesting.*;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;


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
        Reflections reflections = new Reflections("com.itesting");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(GreeterTarget.class);


    }
}









