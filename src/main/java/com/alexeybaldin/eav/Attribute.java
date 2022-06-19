package com.alexeybaldin.eav;

public interface Attribute {

    int getAttributeId();

    String getAttributeName();

    void setAttributeName(String attributeName);

    String getAttributeType();

    void setAttributeType(String attributeType);
}
