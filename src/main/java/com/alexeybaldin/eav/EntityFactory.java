package com.alexeybaldin.eav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
public class EntityFactory {

    private static EntityRepository entityRepository;
    private static AttributeRepository attributeRepository;

    @Autowired
    EntityFactory(EntityRepository entityRepository, AttributeRepository attributeRepository) {
        EntityFactory.entityRepository = entityRepository;
        EntityFactory.attributeRepository = attributeRepository;
    }

    public static Entity createEntity(String entityName) throws Exception {
        if(entityRepository.existsByEntityName(entityName)) {
            throw new Exception("Entity with name='" + entityName + "' already exists");
        }

        EntityImpl entity = new EntityImpl(0, entityName);
        entity.setAttributes(new HashSet<>());
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

    public static Entity addNewEntityAttribute(String entityName, Attribute addAttribute) throws Exception {
        EntityImpl entity = entityRepository.findByEntityName(entityName).orElseThrow(() -> new Exception("Entity not found"));

        System.out.println("===");

        entity.addAttribute(addAttribute);

        System.out.println("=====");

        return entityRepository.save(entity);
    }

    public static Entity removeEntityAttribute(String entityName, Attribute removeAttribute) throws Exception {
        EntityImpl entity = entityRepository.findByEntityName(entityName).orElseThrow(() -> new Exception("Entity not found"));

        entity.removeAttribute(removeAttribute);

        return entityRepository.save(entity);
    }

    public static Entity deleteEntityByName(String entityName) throws Exception {
        EntityImpl entity = entityRepository.findByEntityName(entityName).orElseThrow(() -> new Exception("Entity not found"));

        entityRepository.delete(entity);

        return entity;
    }

    public static void deleteTestingRows() {
        entityRepository.deleteTestingRows();
    }

}
