package com.zachvoxwattz;

import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.logging.LogService;
import com.zachvoxwattz.core.misc.PortChecker;
import com.zachvoxwattz.core.misc.ServerConfigurations;
import com.zachvoxwattz.core.misc.ShutdownThread;
import com.zachvoxwattz.utils.ValueTypeValidator;

/**
 * Entry point of the game server.
 */
public class ApplicationRunner {
    /**
     * The main server object.
     */
    private static MainServer mainServer;

    /**
     * The very first entry point to this application.
     * @param args - List of string arguments
     */
    public static void main(String[] args) {
        // Parse the given args.
        parseArgs(args);

        // Initializes the logging service.
        LogService.initialize();

        // Performs port availability check.
        PortChecker.execute();

        // Initializes the server.
        initializeServer();
    }

    /**
     * Checks if there exists at least one input argument.
     * @param args - List of string arguments
     */
    protected static void parseArgs(String[] args) {
        if (args.length > 0) {
            // Processes the first passed argument as server's target port.
            if (ValueTypeValidator.isInteger(args[0])) {
                ServerConfigurations.PORT_NUMBER(Integer.parseInt(args[0]));
            }

            // Processes the second passed argument as server's debug mode.
            if (ValueTypeValidator.isBoolean(args[1])) {
                ServerConfigurations.DEBUG_MODE(Boolean.parseBoolean(args[1]));
            }
        }
    }

    /**
     * Entry point of server initialization.
     */
    protected static void initializeServer() {
        // Initializes the server and the shutdown hook for graceful shutdown.
        mainServer = new MainServer();

        // Attaches the shutdown hook to Runtime.
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(mainServer));
    }
}
