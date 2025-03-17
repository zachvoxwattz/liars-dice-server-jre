package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
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
 * <p>To be implemented by other classes for diverse functionalities.
 */
public abstract class AbstractHandler<T> implements DataListener<T> {
    /**
     * Denotes whether this handler requires authentication or not.
     */
    protected boolean requiresAuth;

    /**
     * The main server.
     */
    protected MainServer mainServer;

    /**
     * The event string provider
     */
    protected EventStringProvider eventStringProvider;

    /**
     * Constructor for data listener creation.
     * @param mainServer - The main server object to be used.
     * @param requiresAuthentication - boolean value to determine if this handler requires authentication before proceeding.
     */
    public AbstractHandler(MainServer mainServer, boolean requiresAuthentication) {
        this.mainServer = mainServer;
        this.eventStringProvider = mainServer.getEventStringProvider();
        this.requiresAuth = requiresAuthentication;
    }

    public AbstractHandler(MainServer mainServer) {
        this(mainServer, false);
    }

    @Override
    public void onData(SocketIOClient client, T data, AckRequest ackSender) throws Exception {
        /*
         * If this handler needs authentication,
         * executes this block first. 
         */
        if (this.requiresAuth) {
            
        }

        this.onEventExecution(client, data, ackSender);
    }

    /**
     * Executes whenever there is incoming client request.
     * @param client - The requesting client.
     * @param data - Generic data type. To be converted to Java object.
     * @param ackSender - ACK related object.
     */
    public abstract void onEventExecution(SocketIOClient client, T data, AckRequest ackSender);

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
