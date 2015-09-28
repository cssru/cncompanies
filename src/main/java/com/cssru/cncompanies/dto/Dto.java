package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.annotation.MappedAs;
import com.cssru.cncompanies.annotation.NotMapped;
import com.cssru.cncompanies.exception.DtoMappingException;

import java.lang.reflect.Field;

public class Dto {

    // only for Object fields, not for primitives
    public void mapTo(Object entity) throws DtoMappingException {
        Field[] localFields = this.getClass().getDeclaredFields();
        for (Field nextField : localFields) {

            if (!nextField.isAnnotationPresent(NotMapped.class)) {
                String entityFieldName;

                if (nextField.isAnnotationPresent(MappedAs.class)) {
                    entityFieldName = nextField.getAnnotation(MappedAs.class).propertyName();
                } else {
                    entityFieldName = nextField.getName();
                }

                try {
                    Field entityField = entity.getClass().getField(entityFieldName);
                    boolean accessible = entityField.isAccessible();
                    entityField.setAccessible(true);
                    entityField.set(entity, nextField.get(this));
                    entityField.setAccessible(accessible);
                } catch (NoSuchFieldException e) {
                    throw new DtoMappingException("No such field in entity object: " + entityFieldName);
                } catch (IllegalAccessException iae) {
                    throw new DtoMappingException("Illegal access to Dto's field: " + nextField.getName());
                }

            }
        }
    }

    // only for Object fields, not for primitives
    public void mapFrom(Object entity) {
        Field[] localFields = this.getClass().getDeclaredFields();
        for (Field nextField : localFields) {

            if (!nextField.isAnnotationPresent(NotMapped.class)) {
                String entityFieldName;

                if (nextField.isAnnotationPresent(MappedAs.class)) {
                    entityFieldName = nextField.getAnnotation(MappedAs.class).propertyName();
                } else {
                    entityFieldName = nextField.getName();
                }

                try {
                    Field entityField = entity.getClass().getField(entityFieldName);
                    boolean accessible = nextField.isAccessible();
                    nextField.setAccessible(true);
                    nextField.set(this, entityField.get(entity));
                    nextField.setAccessible(accessible);
                } catch (NoSuchFieldException e) {
                    throw new DtoMappingException("No such field in entity object: " + entityFieldName);
                } catch (IllegalAccessException iae) {
                    throw new DtoMappingException("Illegal access to Dto's field: " + nextField.getName());
                }

            }
        }
    }

}
