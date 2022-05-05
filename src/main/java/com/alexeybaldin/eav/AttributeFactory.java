package com.alexeybaldin.eav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AttributeFactory {

    private static AttributeRepository attributeRepository;

    @Autowired
    AttributeFactory(AttributeRepository attributeRepository) {
        AttributeFactory.attributeRepository = attributeRepository;
    }

    public static Attribute createAttribute(String attributeName, String attributeType) throws Exception {
        if(attributeRepository.existsByAttributeName(attributeName)) {
            throw new Exception("Attribute with name='" + attributeName + "' already exists");
        }

        if(!("int".equalsIgnoreCase(attributeType) || "string".equalsIgnoreCase(attributeType))) {
            throw new Exception("Wrong attribute type. Current='" + attributeType + "' Available: 'int'|'string'.");
        }

        AttributeImpl attribute = new AttributeImpl(0, attributeName, attributeType);
        return attributeRepository.save(attribute);
    }

    public static Attribute getAttributeByName(String attributeName) throws Exception {
        return attributeRepository.findByAttributeName(attributeName).orElseThrow(() -> new Exception("Attribute not found"));
    }

    public static Attribute setNewAttributeName(String oldAttributeName, String newAttributeName) throws Exception {
        AttributeImpl attribute = attributeRepository.findByAttributeName(oldAttributeName).orElseThrow(() -> new Exception("Attribute not found"));

        attribute.setAttributeName(newAttributeName);

        return attributeRepository.save(attribute);
    }

    public static Attribute setNewAttributeType(String attributeName, String newAttributeType) throws Exception {
        AttributeImpl attribute = attributeRepository.findByAttributeName(attributeName).orElseThrow(() -> new Exception("Attribute not found"));

        if(!("int".equalsIgnoreCase(newAttributeType) || "string".equalsIgnoreCase(newAttributeType))) {
            throw new Exception("Wrong attribute type. Current='" + newAttributeType + "' Available: 'int'|'string'.");
        }

        attribute.setAttributeType(newAttributeType);

        return attributeRepository.save(attribute);
    }

    public static Attribute deleteAttributeByName(String attributeName) throws Exception {
        AttributeImpl attribute = attributeRepository.findByAttributeName(attributeName).orElseThrow(() -> new Exception("Attribute not found"));

        attributeRepository.delete(attribute);

        return attribute;
    }

    public static void deleteTestingRows() {
        attributeRepository.deleteTestingRows();
    }
}
