package com.alexeybaldin.eav.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityFactory {

    private static EntityRepository entityRepository;

    @Autowired
    EntityFactory(EntityRepository entityRepository) {
        EntityFactory.entityRepository = entityRepository;
    }

    public static Entity createEntity(String entityName) throws Exception {
        if(entityRepository.existsByEntityName(entityName)) {
            throw new Exception("Entity with name='" + entityName + "' already exists");
        }

        EntityImpl entity = new EntityImpl(0, entityName);
        return entityRepository.save(entity);
    }

    public static Entity getEntityByName(String entityName) throws Exception {
        return entityRepository.findByEntityName(entityName).orElseThrow(() -> new Exception("Entity not found"));
    }

    public static Entity setNewEntityName(String oldEntityName, String newEntityName) throws Exception {
        EntityImpl entity = entityRepository.findByEntityName(oldEntityName).orElseThrow(() -> new Exception("Entity not found"));

        entity.setEntityName(newEntityName);

        return entityRepository.save(entity);
    }

    public static Entity deleteEntityByName(String entityName) throws Exception {
        EntityImpl entity = entityRepository.findByEntityName(entityName).orElseThrow(() -> new Exception("Entity not found"));

        entityRepository.delete(entity);

        return entity;
    }

}
