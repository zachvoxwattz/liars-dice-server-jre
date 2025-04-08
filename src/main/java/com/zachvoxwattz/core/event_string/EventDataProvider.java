package com.zachvoxwattz.core.event_string;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
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

        // Processes the event map.
        try {
            // Read the json file and append the entries to the hash map.
            ObjectMapper mapper = new ObjectMapper();
            List<EventDataEntry> eventEntries = mapper.readValue(
                this.getClass().getResourceAsStream("/events/entries.json"),
                new TypeReference<List<EventDataEntry>>() {}
            );

            // Iterate through each entry of the list.
            eventEntries.forEach((entry) -> {
                System.out.println(entry.getAuth());
                eventsMap.put(entry.getKey(), entry);
            });
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
