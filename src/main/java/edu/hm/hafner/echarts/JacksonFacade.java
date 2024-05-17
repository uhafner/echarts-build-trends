package edu.hm.hafner.echarts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Facade for Jackson that does wrap an exception into a {@link RuntimeException}.
 *
 * @author Ullrich Hafner
 */
public class JacksonFacade {
    private final ObjectMapper mapper;

    /**
     * Creates a new instance of {@link JacksonFacade}.
     */
    public JacksonFacade() {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Creates a JSON representation of the specified bean using Jackson data binding.
     *
     * @param bean
     *         the bean to convert
     *
     * @return the JSON representation (as a String)
     */
    public String toJson(final Object bean) {
        try {
            return mapper.writeValueAsString(bean);
        }
        catch (JsonProcessingException exception) {
            throw new IllegalArgumentException(
                    String.format("Can't convert %s to JSON object", bean), exception);
        }
    }

    /**
     * Creates a bean from the specified JSON string.
     *
     * @param json
     *         the bean properties given as JSON string
     * @param type
     *         the type of the bean
     * @param <T>
     *         type of the bean
     *
     * @return the JSON representation (as a String)
     */
    public <T> T fromJson(final String json, final Class<T> type) {
        try {
            return mapper.readValue(json, type);
        }
        catch (JsonProcessingException exception) {
            throw new IllegalArgumentException(
                    String.format("Can't convert JSON '%s' to bean", json), exception);
        }
    }

    /**
     * Returns the text value of the specified JSON property.
     *
     * @param json
     *         the JSON object to extract the property value from
     * @param property
     *         the name of the property
     * @param defaultValue
     *         the default value if the property is undefined or invalid
     *
     * @return the value of the property
     */
    public String getString(final String json, final String property, final String defaultValue) {
        try {
            JsonNode typeNode = getPropertyAsNode(json, property);
            if (typeNode != null) {
                return typeNode.asText(defaultValue);
            }
        }
        catch (JsonProcessingException exception) {
            // ignore
        }

        return defaultValue;
    }

    /**
     * Returns the text value of the specified JSON property.
     *
     * @param json
     *         the JSON object to extract the property value from
     * @param property
     *         the name of the property
     * @param defaultValue
     *         the default value if the property is undefined or invalid
     *
     * @return the value of the property
     */
    public int getInteger(final String json, final String property, final int defaultValue) {
        try {
            JsonNode typeNode = getPropertyAsNode(json, property);
            if (typeNode != null) {
                return typeNode.asInt(defaultValue);
            }
        }
        catch (JsonProcessingException exception) {
            // ignore
        }

        return defaultValue;
    }

    @CheckForNull
    private JsonNode getPropertyAsNode(final String json, final String property)
            throws JsonProcessingException {
        ObjectNode node = mapper.readValue(json, ObjectNode.class);
        return node.get(property);
    }

    /**
     * Returns the boolean value of the specified JSON property.
     *
     * @param json
     *         the JSON object to extract the property value from
     * @param property
     *         the name of the property
     * @param defaultValue
     *         the default value if the property is undefined or invalid
     *
     * @return the value of the property
     */
    public boolean getBoolean(final String json, final String property, final boolean defaultValue) {
        try {
            JsonNode typeNode = getPropertyAsNode(json, property);
            if (typeNode != null) {
                return typeNode.asBoolean(defaultValue);
            }
        }
        catch (JsonProcessingException exception) {
            // ignore
        }

        return defaultValue;
    }
}
