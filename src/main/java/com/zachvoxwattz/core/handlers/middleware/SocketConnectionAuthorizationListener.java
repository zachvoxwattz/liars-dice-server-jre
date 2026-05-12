package com.zachvoxwattz.core.handlers.middleware;

import java.util.List;
import java.util.Map.Entry;

import com.socketio4j.socketio.AuthorizationListener;
import com.socketio4j.socketio.AuthorizationResult;
import com.socketio4j.socketio.HandshakeData;

import com.zachvoxwattz.core.ClientManager;
import com.zachvoxwattz.core.logging.LogSentry;

/**
 * Middleware for verifying various mandatory conditions of
 * connected client.
 */
public class SocketConnectionAuthorizationListener implements AuthorizationListener {
    /**
     * Parent object.
     */
    private ClientManager clientManager;

    public SocketConnectionAuthorizationListener(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData data) {
        var urlParams = data.getUrlParams();
        var test = data.getHttpHeaders();

        String urlParamsMessage = String.format("--- Begin URL Params ---\n");
        for (Entry<String, List<String>> iterator: urlParams.entrySet()) {
            urlParamsMessage += String.format("\n\t%s:", iterator.getKey());

            if (iterator.getValue().size() > 0) {
                for (String item: iterator.getValue()) {
                    urlParamsMessage += String.format("\n\t - %s", item);
                }
            }
        }
        
        LogSentry.logDebug("%s", urlParamsMessage);
        LogSentry.logDebug("Auth token: %s", test.get("X-ClientId"));
        return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
    }

    /**
     * @return {@code ClientManager} object
     */
    public ClientManager getClientManager() {
        return this.clientManager;
    }
}
