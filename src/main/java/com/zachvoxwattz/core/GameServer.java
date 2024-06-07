package com.zachvoxwattz.core;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

/**
 * The main game server.
 */
public class GameServer extends SocketIOServer {

    /**
     * Constructor initializing the Socket.IO server.
     * @param configuration Object containing many properties
     * for the server to boot and run.
     */
    public GameServer(Configuration configuration) {
        super(configuration);
    }
    
}
