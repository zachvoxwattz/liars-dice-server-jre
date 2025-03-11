package com.zachvoxwattz.core.event_manager;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.zachvoxwattz.core.event_manager.type.RequestEventType;
import com.zachvoxwattz.core.event_manager.type.ResponseEventType;

/**
 * Responsible for managing all of the possible request and response event names.
 * 
 * <p>
 * Shared across the entire application.
 * </p>
 */
public class EventNameManager {
    // Tracks whether the class is initialized or not.
    private static boolean isInitialized = false;

    // List of possible client request event names
    private static Map<RequestEventType, String> clReqEventsMap;

    // List of possible server response event names
    private static Map<ResponseEventType, String> svResEventsMap;

    /**
     * Initializes the event name manager.
     */
    public static void initialize() {
        // Initializes the client request event map.
        clReqEventsMap = new HashMap<>();
        clReqEventsMap.put(RequestEventType.NON_EXIST_TEST, null);
        
        // Initializes the server response event map.
        svResEventsMap = new HashMap<>();
        svResEventsMap.put(ResponseEventType.NON_EXIST_TEST, null);

        // Sets the flag to true.
        isInitialized = true;
    }

    /**
     * Processes to show error in terminal.
     * @throws {@code IllegalAccessError} if component has not been initialized.
     */
    private static void HandleUninitialized() {
        if (!isInitialized) throw new IllegalAccessError("EventNameManager has not been initialized yet.");
    }


    public static String getCLEvent(RequestEventType type) {
        HandleUninitialized();

        var foundValue = clReqEventsMap.get(type);
        if (foundValue.equals(null)) {
            var reportMessage = String.format("No event name found for request type '%s'", type.toString());
            throw new NoSuchElementException(reportMessage);
        }
        
        else return foundValue;
    }

    public static String getSVEvent(ResponseEventType type) {
        HandleUninitialized();

        var foundValue = svResEventsMap.get(type);
        if (foundValue.equals(null)) {
            var reportMessage = String.format("No event name found for response type '%s'", type.toString());
            throw new NoSuchElementException(reportMessage);
        }
        
        else return foundValue;
    }
}
