package com.zachvoxwattz.core.event_string.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model for parsing JSON event string entries.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDataEntry {
    private String key;
    private String value;
    private String setAuth;

    public EventDataEntry() {}

    /**
     * Constructor for this model
     * @param key - Key of the event entry.
     * @param value - Value of the event entry.
     * @param requiresAuth - Denotes whether this event needs authentication.
     */
    public EventDataEntry(String key, String value, String setAuth) {
        this.key = key;
        this.value = value;
        this.setAuth = setAuth;
    }

    /**
     * Getter of the entry key
     * @return The key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Setter for the entry key
     * @param key - To be set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter of the entry value
     * @return The value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Setter for the entry value
     * @param value - To be set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter of the entry value
     * @return The value
     */
    public String getAuth() {
        return this.setAuth;
    }

    /**
     * Setter for the entry value
     * @param value - To be set
     */
    public void setAuth(String value) {
        this.setAuth = value;
    }

    /**
     * Getter of the entry requires auth property.
     * @return The value
     */
    public boolean requiresAuth() {
        return true;
    }
}
