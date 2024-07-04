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
    public static String REQ_EVENT_NAME = "cl-req-wesockey";

    /**
     * Response event name.
     */
    public static String RES_EVENT_NAME = "sv-res-wesockey";

    public WebSocketKeyProvider(GameServer mainServer) {
        super(mainServer);
    }

    @Override
    public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
        var toBeSentDatagram = new WebSocketKeyResponseDatagram(WebSocketKeyGenerator.generateKey());
        client.sendEvent(RES_EVENT_NAME, toBeSentDatagram);

        this.getMainServer().debugPrintf("WebSocketKey '{}' generated for client '{}'", toBeSentDatagram.getWsKey(), client.getSessionId());
    }
}
