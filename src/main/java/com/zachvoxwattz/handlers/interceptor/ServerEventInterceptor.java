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

        // Kicks a client if they don't have an auth token attached when requesting for specified events.
        if (this.eventStringProvider.eventNameRequiresAuthentication(netCode)) {
            String clAuthToken = (String) client.getHandshakeData().getAuthToken();

            if (clAuthToken.equals(null)) {
                ErrorResponseDatagram resDatagram = new ErrorResponseDatagram(403, "Authentication token missing");
                client.sendEvent(
                    this.eventStringProvider.getSVEvent(ResVar.NO_AUTH_TOKEN),
                    resDatagram
                );

                client.disconnect();
                LogService.logWarning("Client '%s' tried to make a request without an authentication token. Rejecting connection.", client.getSessionId());
            }

            else {
                // TODO: handle token validation here!
                System.out.println();
            }
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
