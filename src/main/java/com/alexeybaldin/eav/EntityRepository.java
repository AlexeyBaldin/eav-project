package com.alexeybaldin.eav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
interface EntityRepository extends JpaRepository<EntityImpl, Integer> {

    Optional<EntityImpl> findByEntityName(String entityName);

    Boolean existsByEntityName(String entityName);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM entity WHERE entity_name LIKE '%Testing%'", nativeQuery = true)
    void deleteTestingRows();
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
        if(this.attributes == null) {
            this.attributes = new HashSet<>();
        }
        this.attributes.add((AttributeImpl) addAttribute);
    }

    @Override
    public void removeAttribute(Attribute removeAttribute) {
        if(this.attributes == null) {
            this.attributes = new HashSet<>();
        }
        this.attributes.removeIf(attribute-> attribute.equals(removeAttribute));
    }

    @Override
    public void setAttributes(Set<Attribute> attributes) {
        if(this.attributes == null) {
            this.attributes = new HashSet<>();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityImpl entity = (EntityImpl) o;
        return entityName.equals(entity.entityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName);
    }
}
