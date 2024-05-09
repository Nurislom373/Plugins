package org.khasanof.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.khasanof.DataFieldsMapper;
import org.khasanof.field.Property;
import org.khasanof.mediator.interceptor.ObjectFieldInterceptorMediator;
import org.khasanof.mediator.object.ObjectFieldFactoryMediator;
import org.khasanof.processor.type.FieldTypeStrategy;
import org.khasanof.reader.DataFieldsReader;
import org.khasanof.registry.FieldTypeRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author Nurislom
 * @see org.khasanof.processor
 * @since 4/23/2024 6:01 PM
 */
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class DataFieldsJsonMapper implements DataFieldsMapper {

    private final ObjectMapper objectMapper;
    private final DataFieldsReader dataFieldsReader;
    private final FieldTypeRegistry fieldTypeRegistry;
    private final ObjectFieldFactoryMediator fieldFactoryMediator;
    private final ObjectFieldInterceptorMediator objectFieldInterceptorMediator;

    public DataFieldsJsonMapper(ObjectMapper objectMapper,
                                DataFieldsReader dataFieldsReader,
                                FieldTypeRegistry fieldTypeRegistry,
                                ObjectFieldFactoryMediator fieldFactoryMediator,
                                ObjectFieldInterceptorMediator objectFieldInterceptorMediator) {

        this.objectMapper = objectMapper;
        this.dataFieldsReader = dataFieldsReader;
        this.fieldTypeRegistry = fieldTypeRegistry;
        this.fieldFactoryMediator = fieldFactoryMediator;
        this.objectFieldInterceptorMediator = objectFieldInterceptorMediator;
    }

    @Override
    public String transform(Class<?> type) {
        JSONArray jsonArray = new JSONArray();
        List<Property> properties = dataFieldsReader.getDataFields(type);
        iterateDataFields(properties, jsonArray);
        return tryJsonArrayMapToString(jsonArray);
    }

    private String tryJsonArrayMapToString(JSONArray jsonArray) {
        try {
            return objectMapper.writeValueAsString(jsonArray);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void iterateDataFields(List<Property> properties, JSONArray jsonArray) {
        properties.forEach(field -> processDataField(field, jsonArray));
    }

    private void processDataField(Property property, JSONArray jsonArray) {
        Set<FieldTypeStrategy> fieldTypeStrategies = fieldTypeRegistry.getAll();
        fieldTypeStrategies.forEach(strategy -> {
            if (strategy.condition(property)) {
                internalProcessDataField(property, jsonArray, strategy);
            }
        });
    }

    private void internalProcessDataField(Property property, JSONArray jsonArray, FieldTypeStrategy strategy) {
        fieldFactoryMediator.create(property, strategy.getType())
                .ifPresent(objectField -> {
                    jsonArray.appendElement(objectField);
                    strategy.action(objectField, property);
                    objectFieldInterceptorMediator.intercept(objectField, property);
                });
    }
}
