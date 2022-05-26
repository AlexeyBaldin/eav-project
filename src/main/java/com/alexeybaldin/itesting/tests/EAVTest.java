package com.alexeybaldin.itesting.tests;

import com.alexeybaldin.eav.Attribute;
import com.alexeybaldin.eav.AttributeFactory;
import com.alexeybaldin.eav.Entity;
import com.alexeybaldin.eav.EntityFactory;
import com.alexeybaldin.itesting.annotations.MyClearMethod;
import com.alexeybaldin.itesting.annotations.MyTest;
import com.alexeybaldin.itesting.annotations.MyTesterTarget;


@MyTesterTarget
public class EAVTest {

    @MyClearMethod
    public static void clearDatabase() {
        AttributeFactory.deleteTestingRows();
        EntityFactory.deleteTestingRows();
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
