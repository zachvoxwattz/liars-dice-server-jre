package com.zachvoxwattz.core.misc;

import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

import com.zachvoxwattz.core.logging.LogService;

public class PortChecker {
    /**
     * Executes the entire checking process of a given port.
     * <p>
     * Prints additional details to user if debug mode is enabled.
     */
    public static void execute() {
        /*
            Declares some working variables to
            check port availability.
        */
        var portIsAvailable = false;
        var retryCount = 0;
        var retryCountMax = 5;
        
        // Checking whether the target port is available.
        LogService.logDebug("Checking port %s availability...", ServerConfigurations.PORT_NUMBER);

        while (retryCount < retryCountMax) {
            portIsAvailable = isPortAvailable(ServerConfigurations.PORT_NUMBER);
            
            if (portIsAvailable) break;
            else {
                LogService.logError("Port %s is currently unavailable. Retrying...", ServerConfigurations.PORT_NUMBER);
                if (++retryCount == retryCountMax) break;
            }

            try { TimeUnit.SECONDS.sleep(5); }
            catch (Exception e) {}
        }

        if (!portIsAvailable) {
            LogService.logError("Failed to initialize server after %s attempts.", retryCount);
            LogService.logError("Reason: Another application or service is occupying the target port. Please try other alternatives.");
            System.exit(1);
        }

        else LogService.logDebug("Port %s is available.", ServerConfigurations.PORT_NUMBER);
    }

    /**
     * Actual availability test of a given port.
     * @param port The value to be checked.
     * @return {@code true} if the port is available. Otherwise, {@code false}.
     */
    public static boolean isPortAvailable(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        }
        catch (Exception e) { return false; }
    }
}
