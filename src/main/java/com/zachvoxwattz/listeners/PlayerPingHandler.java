package com.zachvoxwattz.listeners;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.datagrams.PingResponseDatagram;

/**
 * Incoming event handler for processing ping requests.
 */
public class PlayerPingHandler implements DataListener<Integer> {
    /**
     * Shared static event names for easy access. 
     */
    public static String EVENT_NAME = "cl-req-ping";
    private static String RESPONSE_EVENT_NAME = "sv-res-ping";

    /**
     * Parent component.
     */
    private GameServer mainServer;
    /**
     * SocketIOServer instance.
     */
    private SocketIOServer socketIOInstance;

    public PlayerPingHandler(GameServer parentComponent) {
        this.mainServer = parentComponent;
        this.socketIOInstance = parentComponent.getSocketIOInstance();
    }

    @Override
    public void onData(SocketIOClient cl, Integer data, AckRequest ackSender) throws Exception {
        var toBeSentData = new PingResponseDatagram(69420);
        // this.socketIOInstance.getBroadcastOperations().sendEvent(RESPONSE_EVENT_NAME, toBeSentData);
        this.socketIOInstance.getBroadcastOperations().sendEvent(RESPONSE_EVENT_NAME, toBeSentData, cl);

        if (mainServer.getDebugMode()) {
            var clientID = cl.getSessionId();
            mainServer.getLogger().debug("Client '{}' invoked ping request. Responded with 69420.", clientID);
        }
    }
    
}
