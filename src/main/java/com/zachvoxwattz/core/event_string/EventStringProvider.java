package com.zachvoxwattz.core.event_string;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.corundumstudio.socketio.namespace.EventEntry;
import com.corundumstudio.socketio.protocol.Event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zachvoxwattz.core.event_string.entity.ReqVar;
import com.zachvoxwattz.core.event_string.entity.ResVar;
import com.zachvoxwattz.core.logging.LogService;

/**
 * Responsible for managing all of the possible request and response event names.
 * 
 * <p>
 * Shared across the entire application.
 * </p>
 */
public class EventStringProvider {
    // Tracks whether the class is initialized or not.
    private boolean isInitialized = false;

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

        // Retrieves the .json file.
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<EventEntry> eventEntries = Arrays.asList(
                mapper.readValue(Paths.get("entries.json").toFile(),
                EventEntry[].class
            ));
        }

        catch (Exception ex) {
            LogService.logCritical("An error occurred while trying to parse events JSON list.\n\n%s", ex.getMessage());
        }

        
        // Sets the flag to true.
        isInitialized = true;
    }

    /**
     * Verifies the existence of a value of an entry in request map.
     * @param eventName The event string to be tested.
     * @return {@code true} if exists.
     */
    public boolean reqStringExists(String eventName) {
        return this.clReqEventsMap.containsValue(eventName);
    }

    public boolean eventNameRequiresAuthentication(String eventName) {
        return this.authRequiredList.contains(eventName);
    }

    /**
     * Processes to show error in terminal.
     * @throws {@code IllegalAccessError} if component has not been initialized.
     */
    private void HandleUninitialized() {
        if (!isInitialized) throw new IllegalAccessError("EventNameManager has not been initialized yet.");
    }


    public String getCLEvent(ReqVar type) {
        HandleUninitialized();
        return clReqEventsMap.get(type);
    }

    public String getSVEvent(ResVar type) {
        HandleUninitialized();
        return svResEventsMap.get(type);
    }
}
