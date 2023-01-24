package com.peoplespheres.model;

// Jackson imports

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.peoplespheres.configuration.json.PsoObjectDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * PSO object class to manage generic content allowing to be pretty flexible
 */
@JsonDeserialize(using = PsoObjectDeserializer.class)
@Slf4j
public class PSObject {
    /**
     * The list of key value pairs with the properties of the client generic object
     */
    SortedMap<String, Object> content = new TreeMap<>();

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////
    @JsonAnyGetter
    public SortedMap<String, Object> getContent() {
        return content;
    }

    @JsonAnySetter
    public void setContent(final SortedMap<String, Object> content) {
        this.content = content;
    }

    /**
     * Bring back the map containing all the properties in the PSO at the path given as in input parameter.
     *
     * @param pathOperation The path giving the level in the PSO where we want to get the properties.
     * @return Map<String, Object> - Map containing the properties at the level given by the input path.
     */
    public Map<String, Object> getReferenceMap(@NonNull final String pathOperation) {
        // Getting the string value to truncate using the path
        final String[] psoLevels = pathOperation.split("\\.");
        Map<String, Object> currentReference = content;
        for (int psoLevelIndex = 0; psoLevelIndex < (psoLevels.length - 1); psoLevelIndex++) {
            String[] psoLevelSplit = psoLevels[psoLevelIndex].split("[\\[\\]]");
            final String psoLevelKey = psoLevelSplit[0];
            Object psoCurrentLevel = currentReference.get(psoLevelKey);
            if (psoCurrentLevel == null) {
                final String errMsg = String.format("Path absent from the map : (%s , %s)", psoLevelKey, pathOperation);
                log.error(errMsg);
                throw new IllegalStateException(errMsg);
            }
            // Simple PSO object cases
            else if (psoLevelSplit.length == 1) {
                currentReference = ((PSObject) psoCurrentLevel).getContent();
            }
            // List of PSO object cases
            else if (psoLevelSplit[1].equals("x")) {
                currentReference = ((List<PSObject>) psoCurrentLevel).get(0).getContent();
            }
            // List of PSO object cases
            else {
                final int arrayIndex = Integer.parseInt(psoLevelSplit[1]);
                currentReference = ((List<PSObject>) psoCurrentLevel).get(arrayIndex).getContent();
            }
        }
        return currentReference;
    }

    /**
     * Getting the list of categories and properties of the PSO given as an input
     *
     * @param propertyNames The list of properties found in the PSO.
     * @param categoryNames The list of categories found in the PSO.
     */
    public void getPropertyAndCategoryLists(@NonNull Map<String, String> propertyNames, @NonNull Map<String, String> categoryNames, String currentPath) {
        // Getting the list of properties and categories for this level
        final Predicate<Map.Entry<String, Object>> isCategory = (propertyEntry -> (propertyEntry.getValue() instanceof PSObject));
        final Predicate<Map.Entry<String, Object>> isArray = (propertyEntry -> ((propertyEntry.getValue() instanceof List<?>) &&
                (!((List<?>) propertyEntry.getValue()).isEmpty()) && ((List<?>) propertyEntry.getValue()).get(0) instanceof PSObject));
        final var splitProperties = content.entrySet().stream().collect(Collectors.partitioningBy(isCategory));
        final var splitArrays = content.entrySet().stream().collect(Collectors.partitioningBy(isArray));
        var simplePropertyList = splitProperties.get(false).stream().map(Map.Entry::getKey).toList();
        var categoryList = splitProperties.get(true).stream().map(Map.Entry::getKey).toList();
        var propertyArrayList = splitArrays.get(true).stream().map(Map.Entry::getKey).toList();

        // Construction of the categories and properties maps
        final String newPath = (currentPath.isEmpty() ? currentPath : currentPath + ".");
        final var propertyMap = simplePropertyList.stream().collect(Collectors.toUnmodifiableMap(property -> (newPath + property), property -> property));
        final var categoryMap = categoryList.stream().collect(Collectors.toUnmodifiableMap(property -> (newPath + property), property -> property));
        final var propertyArrayMap = propertyArrayList.stream().collect(Collectors.toUnmodifiableMap(property -> (newPath + property + "[x]"), property -> property));
        propertyNames.putAll(propertyMap);
        propertyNames.putAll(propertyArrayMap);
        categoryNames.putAll(categoryMap);

        // Going to the next level
        for (var entryCategory : splitProperties.get(true)) {
            final String newPathCat = newPath + entryCategory.getKey();
            final PSObject subPSObject = (PSObject) entryCategory.getValue();
            subPSObject.getPropertyAndCategoryLists(propertyNames, categoryNames, newPathCat);
        }

        // Going to the next level for the first element of PSO arrays
        for (var entryArray : splitArrays.get(true)) {
            final String newPathArray = newPath + entryArray.getKey() + "[x]";
            final PSObject subPSObject = ((List<PSObject>) entryArray.getValue()).get(0);
            subPSObject.getPropertyAndCategoryLists(propertyNames, categoryNames, newPathArray);
        }
    }

    /**
     * Getting a property value based on its path in the considered PSO object.
     *
     * @param propertyPath The path in the considered PSO for the property we want to get the value.
     * @return String - The string representation of the property value.
     */
    public String getPropertyValue(@NonNull final String propertyPath) {
        final String[] propertyPathParts = propertyPath.split("\\.");
        final String propertyKey = propertyPathParts[propertyPathParts.length - 1];
        Map<String, Object> currentReference = getReferenceMap(propertyPath);
        if (!currentReference.containsKey(propertyKey)) {
            final String errMsg = String.format("Path absent from the map : %s", propertyPath);
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return currentReference.get(propertyKey).toString();
    }

    @Override
    public String toString() {
        return "PSObject{" +
                "content=" + content +
                '}';
    }

    /** Returning the PS key containing the metadata containing the properties describing the workflow associated to the PSO.
     *
     * @return PSObject - The PS key containing the metadata containing the properties describing the workflow associated to the PSO.
     */
    @JsonIgnore
    public PSObject getPSKey() {
        return (PSObject) content.getOrDefault("metadata", null);
    }

    @JsonIgnore
    public boolean isInError() {
        final Object error = content.getOrDefault("psoContextError", null);
        return (error != null);
    }
}
