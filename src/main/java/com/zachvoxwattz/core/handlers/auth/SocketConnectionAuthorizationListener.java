package com.zachvoxwattz.core.handlers.auth;

import java.util.logging.LogManager;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import com.zachvoxwattz.core.ClientManager;
import com.zachvoxwattz.core.logging.LogSentry;

/**
 * Middleware for verifying various mandatory conditions of
 * connected client.
 */
public class SocketConnectionAuthorizationListener implements AuthorizationListener{
    /**
     * Parent object.
     */
    private ClientManager clientManager;

    public SocketConnectionAuthorizationListener(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData data) {
        var url = data.getUrl();
        LogSentry.logDebug("URL: %s", url);

        return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
    }
}
