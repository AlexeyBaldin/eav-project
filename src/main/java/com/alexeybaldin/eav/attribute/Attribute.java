package com.alexeybaldin.eav.attribute;

public interface Attribute {

    int getAttributeId();

    String getAttributeName();

    void setAttributeName(String attributeName);

    String getAttributeType();

    void setAttributeType(String attributeType);
}
