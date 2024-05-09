package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.factories.property.PropertyFactory;
import org.khasanof.field.Property;
import org.khasanof.field.data.ArrayField;
import org.khasanof.processor.type.composite.CompositeFieldTypeStrategy;
import org.khasanof.utils.GenericUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 2:27 PM
 */
@Component
public class CollectionFieldTypeStrategy implements FieldTypeStrategy<ArrayField> {

    private final PropertyFactory propertyFactory;
    private final CompositeFieldTypeStrategy compositeFieldTypeStrategy;

    public CollectionFieldTypeStrategy(PropertyFactory propertyFactory,
                                       CompositeFieldTypeStrategy compositeFieldTypeStrategy) {

        this.propertyFactory = propertyFactory;
        this.compositeFieldTypeStrategy = compositeFieldTypeStrategy;
    }

    @Override
    public boolean condition(Property field) {
        return field.getFieldType().equals(Collection.class) || Collection.class.isAssignableFrom(field.getFieldType());
    }

    @Override
    public void action(ArrayField arrayField, Property property) {
        arrayField.setFieldType(getFieldType(arrayField, property));
    }

    private String getFieldType(ArrayField arrayField, Property property) {
        return compositeFieldTypeStrategy.fieldType(createProperty(property.getField(), arrayField.getFieldName()));
    }

    private Property createProperty(Field field, String fieldName) {
        Class<?> classGenericType = GenericUtils.getFieldGenericType(field);
        return propertyFactory.create(classGenericType, fieldName);
    }

    @Override
    public String fieldType() {
        return GlobalConstants.COLLECTION;
    }
}
