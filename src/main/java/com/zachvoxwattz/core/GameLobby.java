package com.zachvoxwattz.core;

import java.util.HashMap;
import java.util.Map;

import com.zachvoxwattz.models.entities.Player;

public class GameLobby {
    /**
     * Main server instance.
     */
    private GameServer mainServer;

    /**
     * A mapping of players and their ID.
     */
    private Map<String, Player> playerMap;

    public GameLobby(GameServer mainServer) {
        this.mainServer = mainServer;
        this.playerMap = new HashMap<>();
    }

    /**
     * Retrieves the target player based on input ID.
     * @param id Returned player ID.
     * @return A {@code Player} object.
     * @throws Exception 
     */
    public Player getPlayer(String id) {
        if (this.playerMap.containsKey(id)) return this.playerMap.get(id);
        else {
            this.mainServer.getLogger().error("Player");
            return null;
        }
    }
}
