package com.alexeybaldin.eav.attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
interface AttributeRepository extends JpaRepository<AttributeImpl, Integer> {

    Boolean existsByAttributeName(String attributeName);
}

@Entity
@Table(name="attribute")
class AttributeImpl implements Attribute{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="attribute_id")
    private int attributeId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_type")
    private String attributeType;

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
    public String toString() {
        return "AttributeImpl{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeType='" + attributeType + '\'' +
                '}';
    }
}
