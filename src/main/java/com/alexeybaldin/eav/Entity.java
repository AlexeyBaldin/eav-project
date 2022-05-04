package com.alexeybaldin.eav;

import java.util.Set;

public interface Entity {

    int getEntityId();

    String getEntityName();

    void setEntityName(String entityName);

    Set<? extends Attribute> getAttributes();

    void addAttribute(Attribute attribute);

    void removeAttribute(Attribute attribute);

    void setAttributes(Set<Attribute> attributes);
}
