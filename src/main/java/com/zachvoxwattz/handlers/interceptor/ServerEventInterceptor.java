package com.zachvoxwattz.handlers.interceptor;

import java.util.List;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;
import com.zachvoxwattz.core.MainServer;

public class ServerEventInterceptor implements EventInterceptor {
    private MainServer mainServer;

    public ServerEventInterceptor(MainServer mainServer) {
        this.mainServer = mainServer;
    }

    @Override
    public void onEvent(NamespaceClient cl, String code, List<Object> datagram, AckRequest ack) {
        this.mainServer.debugPrintf("\n\t- Client ID: %s\n\t- Net code: %s", cl.getSessionId(), code);
    }
}
