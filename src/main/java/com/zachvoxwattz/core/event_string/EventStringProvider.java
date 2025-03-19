package com.zachvoxwattz.core.event_string;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zachvoxwattz.core.event_string.entity.EventString;
import com.zachvoxwattz.core.logging.LogService;

/**
 * Responsible for managing all of the possible request and response event names.
 * 
 * <p>
 * Shared across the entire application.
 * </p>
 */
public class EventStringProvider {
    // Map of all possible event names
    private Map<String, String> eventsMap;

    // List of authentication required events.
    private List<String> authRequiredEventsList;

    /**
     * Initializes the event name manager.
     */
    public EventStringProvider() {
        // Initializes the event map.
        this.eventsMap = new HashMap<>();

        // Processes the event map.
        try {
            // Read the json file and append the entries to the hash map.
            ObjectMapper mapper = new ObjectMapper();
            List<EventString> eventEntries = mapper.readValue(
                this.getClass().getResourceAsStream("/events/entries.json"),
                new TypeReference<List<EventString>>() {}
            );

            // Iterate through each entry of the list.
            eventEntries.forEach((entry) -> {
                eventsMap.put(entry.getKey(), entry.getValue());

                // If such entry requires authentication, adds to a discrete list.
                if (entry.requiresAuth()) this.authRequiredEventsList.add(entry.getValue());
            });
        }

        catch (Exception ex) {
            LogService.logCritical("An error occurred while trying to parse events JSON list.\n\n%s", ex.getMessage());
        }
    }

    /**
     * Verifies the existence of a value of an entry in request map.
     * @param eventName The event string to be tested.
     * @return {@code true} if exists.
     */
    public boolean eventStringExists(String eventName) {
        return this.eventsMap.containsValue(eventName);
    }

    /**
     * Verifies the existence of a designated event entry which requires authentication.
     * @param eventName The event string to be tested.
     * @return {@code true} if exists.
     */
    public boolean eventNameRequiresAuth(String eventName) {
        return this.authRequiredEventsList.contains(eventName);
    }

    /**
     * Returns the actual value of an event entry associated with the provided key.
     * @param key - Assigned to the event.
     * @return An event {@code String}.
     */
    public String getEventString(String key) {
        return this.eventsMap.get(key);
    }
}
