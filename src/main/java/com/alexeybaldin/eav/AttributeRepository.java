package com.alexeybaldin.eav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
interface AttributeRepository extends JpaRepository<AttributeImpl, Integer> {

    Boolean existsByAttributeName(String attributeName);

    Optional<AttributeImpl> findByAttributeName(String attributeName);
}

@javax.persistence.Entity
@Table(name="attribute")
class AttributeImpl implements Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="attribute_id")
    private int attributeId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_type")
    private String attributeType;

    @ManyToMany(mappedBy = "attributes")
    private Set<EntityImpl> entities;


    public AttributeImpl() {}

    public AttributeImpl(int attributeId, String attributeName, String attributeType) {
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public String getAttributeType() {
        return attributeType;
    }

    @Override
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeImpl attribute = (AttributeImpl) o;
        return attributeName.equals(attribute.attributeName) && attributeType.equals(attribute.attributeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName, attributeType);
    }

    @Override
    public String toString() {
        return "AttributeImpl{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeType='" + attributeType + '\'' +
                '}';
    }
}
