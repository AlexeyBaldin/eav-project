package com.alexeybaldin.test;

import com.alexeybaldin.eav.Attribute;
import com.alexeybaldin.eav.AttributeFactory;
import com.alexeybaldin.eav.Entity;
import com.alexeybaldin.eav.EntityFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.alexeybaldin.constant.Color.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyTest {
}

public class EAVTest {

    private static final ArrayList<Method> methods;

    static {
        methods = new ArrayList<>();
        Collections.addAll(methods, EAVTest.class.getMethods());
    }

    public static void runTests() {
        Class<EAVTest> eavTestClass = EAVTest.class;
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("=========================================================================START TESTING=========================================================================");
        methods.forEach(method -> {
            if(method.isAnnotationPresent(MyTest.class) && method.getReturnType() == boolean.class) {
                stringBuilder.append("[" + ANSI_CYAN).append(method.getDeclaringClass().getName()).append(ANSI_BLUE).append("   ").append(method.getName()).append(ANSI_RESET).append("]");
                try {
                    long time = System.currentTimeMillis();
                    boolean success = (boolean)method.invoke(eavTestClass);
                    time = System.currentTimeMillis() - time;
                    clearDatabase();

                    if(success) {
                        stringBuilder.append(ANSI_GREEN + "   Test Success    " + ANSI_YELLOW).append((double)time / 1000).append(" sec").append(ANSI_RESET).append('\n');
                    } else {
                        stringBuilder.append(ANSI_RED + "    Test Error    " + ANSI_YELLOW).append((double)time / 1000).append(" sec").append(ANSI_RESET).append('\n');
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        });
        System.out.println("============================================================================RESULTS============================================================================");

        System.out.println(stringBuilder);

        System.out.println("========================================================================FINISH TESTING=========================================================================");

    }

    private static void clearDatabase() {
        AttributeFactory.deleteTestingRows();
        EntityFactory.deleteTestingRows();
    }

    @MyTest
    public static boolean colorSuccessTest() {
        return true;
    }

    @MyTest
    public static boolean colorErrorTest() {
        return false;
    }

    @MyTest
    public static boolean attributeBaseCRUDTest() {
        try {
            Attribute attribute = AttributeFactory.createAttribute("Testing", "String");
            Attribute attribute1 = AttributeFactory.getAttributeByName("Testing");
            if(!attribute.equals(attribute1)) {
                return false;
            }
            AttributeFactory.setNewAttributeName("Testing", "UpdateTesting");
            attribute = AttributeFactory.setNewAttributeType("UpdateTesting", "int");
            attribute1.setAttributeName("UpdateTesting");
            attribute1.setAttributeType("int");
            if(!attribute.equals(attribute1)) {
                return false;
            }
            AttributeFactory.deleteAttributeByName("UpdateTesting");
        } catch (Exception e) {
            return false;
        }

        try {
            AttributeFactory.getAttributeByName("UpdateTesting");
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    @MyTest
    public static boolean entityBaseCRUDTest() {
        try {
            Entity entity = EntityFactory.createEntity("Testing");
            Entity entity1 = EntityFactory.getEntityByName("Testing");
            System.out.println("qwe");
            if(!entity.equals(entity1)) {
                return false;
            }
            entity = EntityFactory.setNewEntityName("Testing", "UpdateTesting");
            entity1.setEntityName("UpdateTesting");
            if(!entity.equals(entity1)) {
                return false;
            }
            EntityFactory.deleteEntityByName("UpdateTesting");
        } catch (Exception e) {
            return false;
        }

        try {
            EntityFactory.getEntityByName("UpdateTesting");
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    @MyTest
    public static boolean entityAddRemoveAttributesTest() {
        try {
            Entity entity = EntityFactory.createEntity("Testing");
            Attribute attribute = AttributeFactory.createAttribute("TestingAttribute", "String");

            entity.addAttribute(attribute);
            Entity entity1 = EntityFactory.addNewEntityAttribute("Testing", attribute);

            if(!entity.getAttributes().equals(entity1.getAttributes())) {
                return false;
            }

            entity.removeAttribute(attribute);
            entity1 = EntityFactory.removeEntityAttribute("Testing", attribute);

            if(!entity.getAttributes().equals(entity1.getAttributes())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
