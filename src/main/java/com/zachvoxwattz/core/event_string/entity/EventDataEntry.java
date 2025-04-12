package com.zachvoxwattz.core.event_string.entity;

/**
 * Model for parsing JSON event string entries.
 */
public class EventDataEntry {
    private String key;
    private String value;
    private boolean requiresAuth;

    public EventDataEntry() {}

    /**
     * Constructor for this model
     * @param key - Key of the event entry.
     * @param value - Value of the event entry.
     * @param requiresAuth - Denotes whether this event needs authentication.
     */
    public EventDataEntry(String key, String value, boolean requiresAuth) {
        this.key = key;
        this.value = value;
        this.requiresAuth = requiresAuth;
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
     * Setter for the value key
     * @param key - To be set
     */
    public void setValue(String key) {
        this.value = key;
    }

    /**
     * Setter for the entry value
     * @param value - To be set
     */
    public void requiresAuth(boolean value) {
        this.requiresAuth = value;
    }

    /**
     * Getter of the entry requires auth property.
     * @return The value
     */
    public boolean requiresAuth() {
        return this.requiresAuth;
    }
}
