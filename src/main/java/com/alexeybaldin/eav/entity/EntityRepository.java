package com.alexeybaldin.eav.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;

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


    public EntityImpl() {}

    public EntityImpl(int entityId, String entityName) {
        this.entityId = entityId;
        this.entityName = entityName;
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Override
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityId=" + entityId +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
