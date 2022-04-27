package com.alexeybaldin.eav.attribute;

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

        AttributeImpl entity = new AttributeImpl(0, attributeName, attributeType);
        return attributeRepository.save(entity);
    }
}
