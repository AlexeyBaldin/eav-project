package com.alexeybaldin.eav;

import org.hibernate.annotations.Any;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

public interface Attribute {

    int getAttributeId();

    String getAttributeName();

    void setAttributeName(String attributeName);

    String getAttributeType();

    void setAttributeType(String attributeType);
}
