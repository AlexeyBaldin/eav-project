package com.alexeybaldin.eav;

import com.alexeybaldin.eav.attribute.Attribute;
import com.alexeybaldin.eav.attribute.AttributeFactory;
import com.alexeybaldin.eav.entity.Entity;
import com.alexeybaldin.eav.entity.EntityFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarter {


    public static void main(String[] args) throws Exception {

        SpringApplication.run(SpringBootStarter.class);

        Attribute attribute = AttributeFactory.createAttribute("Test2", "a");

        System.out.println(attribute);
    }
}
