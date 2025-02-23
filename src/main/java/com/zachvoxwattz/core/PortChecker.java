package com.zachvoxwattz.core;

import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

public class PortChecker {
    /**
     * Executes the entire checking process of a given port.
     * <p>
     * Prints additional details to user if debug mode is enabled.
     * @param serverPort The port number to be checked.
     * @param debugMode Boolean value regulating whether to enable the mode or not.
     */
    public static void execute(int serverPort, boolean debugMode) {
        /*
            Declares some working variables to
            check port availability.
        */
        var portIsAvailable = false;
        var retryCount = 0;
        var retryCountMax = 5;
        
        // Checking whether the target port is available.
        if (debugMode) LogService.logDebug("Checking port %s availability...", serverPort);

        while (!portIsAvailable && retryCount < retryCountMax) {
            portIsAvailable = portCheck(serverPort);
            if (portIsAvailable) break;
            else {
                retryCount++;
                if (debugMode) LogService.logDebug("Port %s is currently occupied. Retrying...", serverPort);
            }

            try { TimeUnit.SECONDS.sleep(5); }
            catch (Exception e) {}
        }

        if (!portIsAvailable) {
            LogService.logError("Failed to initialize server after %s attempts.", retryCount);
            LogService.logInfo("Reason: Another servuce is occupying the target port! Please try other alternatives!");
            System.exit(1);
        }

        else if (debugMode) LogService.logDebug("Port %s is available.", serverPort);
    }

    /**
     * Actual availability test of a given port.
     * @param port The value to be checked.
     * @return {@code true} if the port is available. Otherwise, {@code false}.
     */
    public static boolean portCheck(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        }
        catch (Exception e) { return false; }
    }
}
