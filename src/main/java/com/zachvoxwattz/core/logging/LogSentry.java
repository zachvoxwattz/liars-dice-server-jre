package com.zachvoxwattz.core.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.zachvoxwattz.core.shared.ServerConfigurations;

/**
 * Custom made logging component for entire server.
 **/
public class LogSentry {
    private static boolean isInitialized = false;
    private static DateTimeFormatter dateTimeFormatter;

    /**
     * Initializes the custom logging component.
     * <p>
     * Only called once during initialization phase of server. Nothing will happen upon calling it again.
     * </p>
     */
    public static void initialize() {
        if (isInitialized) return;

        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SS");
        isInitialized = true;
        logDebug("Server debug mode enabled.");
        logDebug("Assigned port number %s to the server.", ServerConfigurations.PORT_NUMBER);
    }

    /**
     * Logs a message to terminal. Logging level: <b>Informational</b>
     * @param message - Content to be logged.
     * @param args - Additional required variables for content.
     */
    public static void logInfo(String message, Object... args) {
        HandleUninitialized();
        logConsole(message, LogType.INFO, args); 
    }

    /**
     * Logs a message to terminal. Logging level: <b>Debugging</b>
     * @param message - Content to be logged.
     * @param args - Additional required variables for content.
     */
    public static void logDebug(String message, Object... args) {
        HandleUninitialized();
        if (ServerConfigurations.DEBUG_MODE) logConsole(message, LogType.DEBUG, args); 
    }

    /**
     * Logs a message to terminal. Logging level: <b>Warning</b>
     * @param message - Content to be logged.
     * @param args - Additional required variables for content.
     */
    public static void logWarning(String message, Object... args) {
        HandleUninitialized();
        logConsole(message, LogType.WARNING, args); 
    }

    /**
     * Logs a message to terminal. Logging level: <b>Error</b>
     * @param message - Content to be logged.
     * @param args - Additional required variables for content.
     */
    public static void logError(String message, Object... args) {
        HandleUninitialized();
        logConsole(message, LogType.ERROR, args); 
    }

    /**
     * Logs a message to terminal. Logging level: <b>Critical</b>
     * @param message - Content to be logged.
     * @param args - Additional required variables for content.
     */
    public static void logCritical(String message, Object... args) {
        HandleUninitialized();
        logConsole(message, LogType.CRITICAL, args); 
    }

    private static void HandleUninitialized() {
        if (!isInitialized) throw new IllegalAccessError("LogService has not been initialized yet."); 
    }

    private static String getCurrentTimeStamp() {
        return String.format(LocalDateTime.now().format(dateTimeFormatter));
    }

    private static String retrieveCallingClass() {
        StackTraceElement[] callers = Thread.currentThread().getStackTrace();
        var originalCaller = callers[callers.length - 1].getClassName();

        return originalCaller.substring(originalCaller.lastIndexOf('.') + 1);
    }

    private static String parseLogType(LogType type) {
        switch (type) {
            case INFO:
                // return "\033[38;5;255;48;5;48;1mINFO\033[0m";
                return "INFO";

            case DEBUG:
                // return "\033[38;5;255;48;5;33;1mDEBUG\033[0m";
                return "DEBUG";

            case WARNING:
                // return "\033[38;5;255;48;5;172;1mWARNING\033[0m";
                return "WARNING";

            case ERROR:
                // return "\033[38;5;255;48;5;160;1mERROR\033[0m";
                return "ERROR";

            case CRITICAL:
                // return "\033[38;5;255;48;5;52;1mCRITICAL\033[0m";
                return "CRITICAL";
                
            default:
                try {
                    throw new Exception("Unsupported 'LogServiceType' value");
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static void logConsole(String message, LogType type, Object... args) {
        var outputMessage = String.format(
            "[%s] [%s|%s]: %s",
            getCurrentTimeStamp(),
            retrieveCallingClass(), parseLogType(type), 
            String.format(message, args)
        );

        System.out.println(outputMessage);
    }
}
