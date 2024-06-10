package com.zachvoxwattz.core;

import java.net.ServerSocket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

/**
 * The main game server.
 */
public class GameServer extends SocketIOServer {

    /**
     * Constructor initializing the Socket.IO server.
     * @param configuration Object containing some crucial
     * properties for the server to boot and run.
     */
    public GameServer(Configuration serverConfig) {
        super(serverConfig);
    }

    /**
     * Checks the availability of the given port. 
     * This is a pre-initialization check to make
     * sure the server can start normally.
     * @param port The value to be checked.
     * @return {@code true} if the port is available. Otherwise, {@code false}.
     */
    public static boolean portCheck(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
