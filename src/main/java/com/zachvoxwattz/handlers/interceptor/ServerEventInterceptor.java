package com.zachvoxwattz.handlers.interceptor;

import java.util.List;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.zachvoxwattz.core.MainServer;
import com.zachvoxwattz.core.event_string.EventStringProvider;
import com.zachvoxwattz.core.event_string.type.ResVar;
import com.zachvoxwattz.core.logging.LogService;
import com.zachvoxwattz.datagrams.response.ErrorResponseDatagram;

/**
 * Interceptor for all event oriented connections.
 */
public class ServerEventInterceptor implements EventInterceptor {
    /**
     * Main server instance.
     */
    private MainServer mainServer;

    /**
     * Event string provider instance.
     */
    private EventStringProvider eventStringProvider;

    public ServerEventInterceptor(MainServer mainServer) {
        this.mainServer = mainServer;
        this.eventStringProvider = mainServer.getEventStringProvider();
    }

    @Override
    public void onEvent(NamespaceClient client, String netCode, List<Object> datagram, AckRequest ack) {
        LogService.logDebug("\n\t- Client ID: %s\n\t- Net code: %s", client.getSessionId(), netCode);

        // Sends back to the client an error message if the requested netcode does not exist.
        if (!eventStringProvider.reqStringExists(netCode)) {
            var errorDatagram = new ErrorResponseDatagram(503, String.format("Communication code '%s' is not supported", netCode));
            client.sendEvent(
                this.eventStringProvider.getSVEvent(ResVar.ERR_WRONG_NETCODE),
                errorDatagram
            );

            LogService.logWarning(
                "Client %s tried to request for netcode '%s', which does not exist at all.",
                client.getSessionId(),
                netCode
            );
            return;
        }
    }

    /**
     * Returns the main server instance.
     * @return {@code MainServer} object.
     */
    public MainServer getMainServer() {
        return this.mainServer;
    }
}
