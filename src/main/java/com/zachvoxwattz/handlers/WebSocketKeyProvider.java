package com.zachvoxwattz.handlers;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.datagrams.server_response.WebSocketKeyResponseDatagram;
import com.zachvoxwattz.utils.WebSocketKeyGenerator;

public class WebSocketKeyProvider extends AbstractHandler<String> {
    /**
     * Request event name.
     */
    public static String REQ_EVENT_NAME = "cl-req-wsKey";

    /**
     * Response event name.
     */
    public static String RES_EVENT_NAME = "sv-res-wsKey";

    public WebSocketKeyProvider(GameServer mainServer) {
        super(mainServer);
    }

    @Override
    public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
        var wsKey = WebSocketKeyGenerator.generateKey();
        var clientID = client.getSessionId().toString();

        // Registers it to the UserManager.
        this.getMainServer().getUserManager().registerPlayerWSKey(clientID, wsKey);

        // Then sends the data back to the client.
        client.sendEvent(RES_EVENT_NAME, new WebSocketKeyResponseDatagram(wsKey));
        this.getMainServer().debugPrintf("WebSocketKey '{}' generated for client '{}'", wsKey, clientID);
    }
}
