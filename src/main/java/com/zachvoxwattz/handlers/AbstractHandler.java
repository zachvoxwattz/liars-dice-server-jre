package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.listener.DataListener;
import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.event_string.EventStringProvider;

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
    protected MainServer mainServer;

    /**
     * The event string provider
     */
    protected EventStringProvider eventStringProvider;

    public AbstractHandler(MainServer mainServer) {
        this.mainServer = mainServer;
        this.eventStringProvider = mainServer.getEventStringProvider();
    }

    /**
     * Returns the main server instance.
     * @return {@code MainServer} object
     */
    public MainServer getMainServer() {
        return this.mainServer;
    }

    /**
     * Returns the event string provider instance.
     * @return {@code EventStringProvider} object
     */
    public EventStringProvider getEventStringProvider() {
        return this.eventStringProvider;
    }
}
