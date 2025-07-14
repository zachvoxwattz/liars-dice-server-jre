package com.zachvoxwattz.core.misc;

/**
 * Static class holding all of the configurations of the application.
 */
public class ServerConfigurations {
    /**
     * Denotes the target port for the server to be running on.
     */
    public static int PORT_NUMBER = 11912;

    /**
     * Denotes whether to use debug mode.
     */
    public static boolean DEBUG_MODE = false;

    /**
     * Sets global parameter for port number.
     * @param value - Integer anywhere in range {@code 0 - 65535}.
     * @throws IllegalArgumentException If input value is outside of the denoted range.
     */
    public static void PORT_NUMBER(int value) {
        if (value < 0 || value > 65535) throw new IllegalArgumentException("Port number must not be out of range from 0 to 65535");
        PORT_NUMBER = value;
    }

    /**
     * Sets global parameter for debug mode.
     * @param value - Either {@code true} or {@code false}.
     */
    public static void DEBUG_MODE(boolean value) {
        DEBUG_MODE = value;
    }
}
