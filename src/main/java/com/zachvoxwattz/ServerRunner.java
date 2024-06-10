package com.zachvoxwattz;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.corundumstudio.socketio.Configuration;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.utils.ValueChecker;

/**
 * Entry point of the game server.
 */
public class ServerRunner {
    private static Logger mainLogger = LogManager.getLogger(ServerRunner.class);

    public static void main(String[] args) {
        // Declares default values for the configuration of the server.
        var serverPort = 11911;
        var debugMode = false;

        // Checks if there exists at least one input argument.
        if (args.length != 0) {
            // Processes the second passed argument as server's debug mode.
            if (ValueChecker.isBoolean(args[1])) {
                debugMode = Boolean.parseBoolean(args[1]);
                if (debugMode) mainLogger.debug("Server debug mode enabled.");
            }

            // Processes the first passed argument as server's target port.
            if (ValueChecker.isInteger(args[0])) {
                serverPort = Integer.parseInt(args[0]);
                if (debugMode) mainLogger.debug("Assigned port number " + serverPort + " to the server.");
            }
            else {
                if (debugMode) mainLogger.debug("Assigned default port number " + serverPort + " to the server.");
            }
        }

        // Constructs a Configuration object to be used later.
        var serverConfiguration = new Configuration();
        serverConfiguration.setHostname("0.0.0.0");
        serverConfiguration.setPort(serverPort);

        // Checking whether the designated port is available.
        var portIsAvailable = false;
        if (debugMode) mainLogger.debug("Checking port " + serverPort + " availability...");
        while (!portIsAvailable) {
            portIsAvailable = GameServer.portCheck(serverPort);
            if (debugMode && !portIsAvailable) mainLogger.debug("Port " + serverPort + " is currently occupied... Retrying...");

            try { TimeUnit.SECONDS.sleep(5); }
            catch (Exception e) {}
        }
    }

    public static Runnable serverStarter = new Runnable() {
        @Override
        public void run() {

        }
    };
}
