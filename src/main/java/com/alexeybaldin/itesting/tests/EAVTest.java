package com.alexeybaldin.itesting.tests;

import com.alexeybaldin.eav.Attribute;
import com.alexeybaldin.eav.AttributeFactory;
import com.alexeybaldin.eav.Entity;
import com.alexeybaldin.eav.EntityFactory;
import org.mytestingframework.annotations.*;
import org.mytestingframework.asserts.MyAssert;

import static com.alexeybaldin.constant.Color.ANSI_BLUE;
import static com.alexeybaldin.constant.Color.ANSI_RESET;


@MyTesterTarget
public class EAVTest {


    @MyBeforeAllTests
    @MyAfterTest
    public static void clearDatabase() {
        AttributeFactory.deleteTestingRows();
        EntityFactory.deleteTestingRows();
        System.out.println(ANSI_BLUE + "EAV CLEAR" + ANSI_RESET);
    }

    @MyTest
    public static void attributeBaseCRUDTest() {
        boolean exception = false;
        try {
            Attribute attribute = AttributeFactory.createAttribute("Testing", "String");
            Attribute attribute1 = AttributeFactory.getAttributeByName("Testing");
//            if(!attribute.equals(attribute1)) {
//                return false;
//            }
            MyAssert.assertEquals(attribute, attribute1);

            AttributeFactory.setNewAttributeName("Testing", "UpdateTesting");
            attribute = AttributeFactory.setNewAttributeType("UpdateTesting", "int");
            attribute1.setAttributeName("UpdateTesting");
            attribute1.setAttributeType("int");
//            if(!attribute.equals(attribute1)) {
//                return false;
//            }
            MyAssert.assertEquals(attribute, attribute1);
            AttributeFactory.deleteAttributeByName("UpdateTesting");
        } catch (Exception e) {
            exception = true;
        }
        MyAssert.assertFalse(exception);

        try {
            AttributeFactory.getAttributeByName("UpdateTesting");
            exception = true;
        } catch (Exception ignored) {}
        MyAssert.assertFalse(exception);
    }

//    @MyTest
//    public static void entityBaseCRUDTest() {
//        try {
//            Entity entity = EntityFactory.createEntity("Testing");
//            Entity entity1 = EntityFactory.getEntityByName("Testing");
//            if(!entity.equals(entity1)) {
//                return false;
//            }
//            entity = EntityFactory.setNewEntityName("Testing", "UpdateTesting");
//            entity1.setEntityName("UpdateTesting");
//            if(!entity.equals(entity1)) {
//                return false;
//            }
//            EntityFactory.deleteEntityByName("UpdateTesting");
//        } catch (Exception e) {
//            return false;
//        }
//
//        try {
//            EntityFactory.getEntityByName("UpdateTesting");
//        } catch (Exception e) {
//            return true;
//        }
//
//        return false;
//    }
//
//    @MyTest
//    public static void entityAddRemoveAttributesTest() {
//        try {
//            Entity entity = EntityFactory.createEntity("Testing");
//            Attribute attribute = AttributeFactory.createAttribute("TestingAttribute", "String");
//
//            entity.addAttribute(attribute);
//            Entity entity1 = EntityFactory.addNewEntityAttribute("Testing", attribute);
//
//            if(!entity.getAttributes().equals(entity1.getAttributes())) {
//                return false;
//            }
//
//            entity.removeAttribute(attribute);
//            entity1 = EntityFactory.removeEntityAttribute("Testing", attribute);
//
//            if(!entity.getAttributes().equals(entity1.getAttributes())) {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//
//        return true;
//    }


}
