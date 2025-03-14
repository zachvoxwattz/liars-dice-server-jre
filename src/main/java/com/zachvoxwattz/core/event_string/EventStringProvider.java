package com.zachvoxwattz.core.event_string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    // List of authentication required event names
    private List<String> authRequiredList;

    /**
     * Initializes the event name manager.
     */
    public EventStringProvider() {
        // Initializes the client request event map.
        this.clReqEventsMap = new HashMap<>();
        this.clReqEventsMap.put(ReqVar.NON_EXIST, null);
        this.clReqEventsMap.put(ReqVar.PING, "cl-req-ping");
        this.clReqEventsMap.put(ReqVar.AUTH_TOKEN, "cl-req-authtoken");
        
        // Initializes the server response event map.
        this.svResEventsMap = new HashMap<>();
        this.svResEventsMap.put(ResVar.NON_EXIST, null);
        this.svResEventsMap.put(ResVar.PING, "sv-res-ping");
        this.svResEventsMap.put(ResVar.ERR_WRONG_NETCODE, "sv-res-invalid-netcode");
        this.svResEventsMap.put(ResVar.ERR_NO_CONNECT, "sv-res-deny-connect");
        this.svResEventsMap.put(ResVar.AUTH_TOKEN, "sv-res-authtoken");
        this.svResEventsMap.put(ResVar.NO_AUTH_TOKEN, "sv-res-deny-null-authtoken");

        // Initializes the server authentication required list.
        this.authRequiredList = new ArrayList<>();
        this.authRequiredList.add("cl-req-ping");

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
