package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.listener.DataListener;
import com.zachvoxwattz.core.GameServer;

/**
 * {@code Abstract} request handler to process requests from clients.
 * 
 * <p>A specified datagram type should be used to determine the
 * data structure of the incoming request sent by clients. Using wrong
 * structure may result in unhandled {@code Exception}(s).
 * 
 * <p>Each implemented class should have 2 public static {@code String}
 * properties named {@code REQ_EVENT_NAME} and {@code RES_EVENT_NAME}, respectively.
 * This is done so that the {@code SocketIOServer} instance can retrieve it
 * statically for its initialization phase. Consult the below example:
 * 
 * <pre>
 * {@code
 * public static String REQ_EVENT_NAME = "<event name goes here>";
 * public static String RES_EVENT_NAME = "<event name goes here>";
 * }
 * </pre>
 *  
 * <p>To be implemented by other classes for diverse functionalities.
 */
public abstract class AbstractHandler<T> implements DataListener<T> {
    /**
     * The main server.
     */
    private GameServer mainServer;

    public AbstractHandler(GameServer mainServer) {
        this.mainServer = mainServer;
    }

    /**
     * Getter of the main server instance.
     */
    public GameServer getMainServer() {
        return this.mainServer;
    }
}
