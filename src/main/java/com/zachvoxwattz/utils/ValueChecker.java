package com.zachvoxwattz.utils;

/**
 * Static class consisting a collection of static value checker methods.
 */
public class ValueChecker {
    /**
     * Checks if the input string is an integer.
     * @param value The string to be checked.
     * @return {@code true} if it is an integer. Otherwise, {@code false}.
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value); 
            return true;
        }
        catch (Exception e) { return false; }
    }

    /**
     * Checks if the input string is a boolean.
     * @param value The string to be checked.
     * @return {@code true} if it is a boolean. Otherwise, {@code false}.
     */
    public static boolean isBoolean(String value) {
        try {
            Boolean.parseBoolean(value);
            return true;
        }
        catch (Exception e) { return false; }
    }
}
