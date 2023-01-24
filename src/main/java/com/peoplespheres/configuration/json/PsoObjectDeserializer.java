package com.peoplespheres.configuration.json;

// Jackson imports

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.peoplespheres.model.PSObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * Generic JSON deserializer of ClientObject
 */
@Slf4j
public class PsoObjectDeserializer extends StdDeserializer<PSObject> {
    /**
     * Default constructor
     */
    public PsoObjectDeserializer() {
        this(null);
    }

    /**
     * Explicit constructor specifying the specific PSO object class to consider.
     *
     * @param vc The specific PSO object class to consider.
     */
    public PsoObjectDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Deserialization of a JSON input stream for a PSO object.
     *
     * @param jsonParser             The JSON parser input stream.
     * @param deserializationContext The JSON deserialization context.
     * @return PSObject - The PSO object as the result of the deserialization of the input stream.
     * @throws IOException Exception thrown in case of deserialization issue.
     */
    @Override
    public PSObject deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonNode psoJsonNode = jsonParser.getCodec().readTree(jsonParser);
        return processObjectNode(jsonParser, deserializationContext, psoJsonNode);
    }

    /**
     * Deserialization of a JSON input stream for a PSO object.
     *
     * @param jsonParser             The JSON parser input stream.
     * @param deserializationContext The JSON deserialization context.
     * @param psoJsonNode            The considered JSON node of this deserialization process.
     * @return PSObject - The PSO object as the result of the deserialization of the input stream.
     */
    private PSObject processObjectNode(final JsonParser jsonParser, final DeserializationContext deserializationContext, final JsonNode psoJsonNode) {
        // Deserialization of the JSON node passed as an input
        final SortedMap<String, Object> psoObjectContent = new TreeMap<>();
        final var propertiesIt = psoJsonNode.fields();
        while (propertiesIt.hasNext()) {
            final Map.Entry<String, JsonNode> property = propertiesIt.next();
            final String propertyKey = property.getKey();
            if (propertyKey.equals("action")) continue;
            final JsonNode jsonPropertyValueNode = property.getValue();

            Object propertyValue;
            if (jsonPropertyValueNode.isValueNode()) {
                propertyValue = processSimpleField(jsonPropertyValueNode);
                psoObjectContent.putIfAbsent(propertyKey, propertyValue);
            } else if (jsonPropertyValueNode.isObject()) {
                propertyValue = processObjectNode(jsonParser, deserializationContext, jsonPropertyValueNode);
                psoObjectContent.putIfAbsent(propertyKey, propertyValue);
            } else if (jsonPropertyValueNode.isArray()) {
                List<Object> propertyArray = new ArrayList<>();
                for (JsonNode subNodeItem : jsonPropertyValueNode) {
                    if (subNodeItem.isValueNode()) {
                        propertyValue = processSimpleField(subNodeItem);
                    } else {
                        propertyValue = processObjectNode(jsonParser, deserializationContext, subNodeItem);
                    }
                    propertyArray.add(propertyValue);
                }
                psoObjectContent.putIfAbsent(propertyKey, propertyArray);
            }
        }

        // Returning the PSO object result of this deserialization
        final PSObject psoObject = new PSObject();
        psoObject.setContent(psoObjectContent);
        return psoObject;
    }

    /**
     * Deserialization of a JSON node containing a simple property/field.
     *
     * @param jsonPropertyValueNode The considered JSON node containing a simple property to deserialize.
     * @return Object The object as a result of this deserialization of this JSON node.
     */
    private Object processSimpleField(final JsonNode jsonPropertyValueNode) {
        Object propertyValue;
        if (jsonPropertyValueNode.isTextual()) {
            propertyValue = jsonPropertyValueNode.asText();
        } else if (jsonPropertyValueNode.isIntegralNumber()) {
            propertyValue = jsonPropertyValueNode.numberValue();
        } else {
            propertyValue = jsonPropertyValueNode.doubleValue();
        }
        return propertyValue;
    }

}
