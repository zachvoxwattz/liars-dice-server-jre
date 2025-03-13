package com.zachvoxwattz.core.event_string;

import java.util.HashMap;
import java.util.Map;

import com.zachvoxwattz.core.event_string.type.ReqVar;
import com.zachvoxwattz.core.event_string.type.ResVar;

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

    // List of possible client request event names
    private Map<ReqVar, String> clReqEventsMap;

    // List of possible server response event names
    private Map<ResVar, String> svResEventsMap;

    /**
     * Initializes the event name manager.
     */
    public EventStringProvider() {
        // Initializes the client request event map.
        clReqEventsMap = new HashMap<>();
        clReqEventsMap.put(ReqVar.NON_EXIST, null);
        clReqEventsMap.put(ReqVar.PING, "cl-req-ping");
        
        // Initializes the server response event map.
        svResEventsMap = new HashMap<>();
        svResEventsMap.put(ResVar.NON_EXIST, null);
        svResEventsMap.put(ResVar.PING, "sv-res-ping");
        svResEventsMap.put(ResVar.ERR_WRONG_NETCODE, "sv-res-invalid-netcode");
        svResEventsMap.put(ResVar.ERR_NO_CONNECT, "sv-res-deny-connect");

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
