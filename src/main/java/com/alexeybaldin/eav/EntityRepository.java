package com.alexeybaldin.eav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Repository
interface EntityRepository extends JpaRepository<EntityImpl, Integer> {

    Optional<EntityImpl> findByEntityName(String entityName);

    Boolean existsByEntityName(String entityName);
}

@javax.persistence.Entity
@Table(name="entity")
class EntityImpl implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private int entityId;

    @Column(name = "entity_name")
    private String entityName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "entity_attribute",
        joinColumns = @JoinColumn(name = "entity_id"),
        inverseJoinColumns = @JoinColumn(name = "attribute_id"))
    private Set<AttributeImpl> attributes;

    public EntityImpl() {}

    public EntityImpl(int entityId, String entityName) {
        this.entityId = entityId;
        this.entityName = entityName;
    }

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    @Override
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public Set<? extends Attribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public void addAttribute(Attribute addAttribute) {
        this.attributes.add((AttributeImpl) addAttribute);
    }

    @Override
    public void removeAttribute(Attribute removeAttribute) {
        this.attributes.removeIf(attribute-> attribute.equals(removeAttribute));
    }

    @Override
    public void setAttributes(Set<Attribute> attributes) {
        this.attributes.clear();
        attributes.forEach((attribute -> this.attributes.add((AttributeImpl) attribute)));
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityId=" + entityId +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
