package com.zachvoxwattz.core.event_string;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zachvoxwattz.core.event_string.entity.EventDataEntry;
import com.zachvoxwattz.core.logging.LogService;

/**
 * Responsible for managing all of the possible request and response event names.
 * 
 * <p>
 * Shared across the entire application.
 * </p>
 */
public class EventDataProvider {
    // Map of all possible event names
    private Map<String, EventDataEntry> eventsMap;

    /**
     * Initializes the event name manager.
     */
    public EventDataProvider() {
        // Initializes the event map.
        this.eventsMap = new HashMap<>();

        // Reads the file.
        InputStream fileInputStream = this.getClass().getResourceAsStream("/events/entries.json");
        if (fileInputStream == null) {
            throw new IllegalArgumentException("Event registry file not found");
        }

        // Stringify file contents for next-step processing.
        BufferedReader bfReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        String stringifiedContent = bfReader.lines().collect(Collectors.joining(System.lineSeparator()));

        // Parse them into JSON object.
        try {
            ObjectMapper objectMapper = new ObjectMapper(); // Jackson main object.
            JsonNode arrayNode = objectMapper.readTree(stringifiedContent);

            // Checks to see if this is an array.
            if (arrayNode.isArray()) {
                // It is, starts mapping.
                arrayNode.forEach((node) -> {
                    String key = node.get("key").asText();
                    String value = node.get("value").asText();
                    boolean requiresAuth = node.get("requiresAuth").asBoolean();

                    // Puts each entry into the initialized map.
                    this.eventsMap.put(key, new EventDataEntry(key, value, requiresAuth));
                });
            }
        }

        catch (Exception ex) {
            LogService.logCritical("An error occurred while trying to parse events JSON list.\n\n%s", ex.getMessage());
        }
    }

    /**
     * Returns the map size of registered entries.
     * @return {@code int} number of registered events.
     */
    public int getMapCount() {
        return this.eventsMap.size();
    }

    /**
     * Verifies the existence of a value of an entry in request map.
     * @param eventName The event string to be tested.
     * @return {@code true} if exists.
     */
    public boolean eventStringExists(String eventName) {
        boolean foundEvent = false;
        for (Map.Entry<String, EventDataEntry> item: this.eventsMap.entrySet()) {
            var currentValue = item.getValue().getValue();
            if (eventName.equals(currentValue)) {
                foundEvent = true;
                break;
            }
        }

        return foundEvent;
    }

    /**
     * Returns the object of an event entry associated with the provided key.
     * @param key - Assigned to the event.
     * @return An event {@code EventStringObject}.
     */
    public EventDataEntry getEventData(String key) {
        return this.eventsMap.get(key);
    }

    /**
     * Returns the actual value of an event entry associated with the provided key.
     * @param key - Assigned to the event.
     * @return An event {@code String}.
     */
    public String getEventString(String key) {
        return this.getEventData(key).getValue();
    }
}
