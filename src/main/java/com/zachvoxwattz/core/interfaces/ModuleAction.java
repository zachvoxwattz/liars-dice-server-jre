package com.zachvoxwattz.core.interfaces;

/**
 * Interface for core components of the server application.
 */
public interface ModuleAction {
    /**
     * Shuts down the targeted component.
     */
    public void shutdown();

    /**
     * Starts the targeted component.
     */
    public void initialize();
}
