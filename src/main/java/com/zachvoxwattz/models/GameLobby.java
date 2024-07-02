package com.zachvoxwattz.models;

import java.util.HashMap;
import java.util.Map;

import com.zachvoxwattz.core.GameServer;
import com.zachvoxwattz.datagrams.client_request.RegistrationRequestDatagram;
import com.zachvoxwattz.datagrams.server_response.PlayerUpdateDatagram;
import com.zachvoxwattz.models.entities.Player;

/**
 * Waiting room lobby that holds up to 6 players.
 */
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
     * Adds a new player to the map of this lobby.
     * @param id ID of the player.
     * @param name In-game name of the player.
     * @param avatarID ID of player's chosen avatar.
     * @param avatarBackgroundHex Hex color code of player's avatar background.
     * @param avatarBackgroundAlpha Alpha of the hex color code of player's avatar background.
     * @param diceBodyHex Hex color code of player's dice body.
     * @param diceBodyAlpha Alpha of the hex color code of player's dice body.
     * @param diceFaceHex Hex color code of player's dice face.
     * @param diceFaceAlpha Alpha of the hex color code of player's dice face.
     */
    public void addPlayer(String id,String name,String avatarID,String avatarBackgroundHex,float avatarBackgroundAlpha,String diceBodyHex,float diceBodyAlpha,String diceFaceHex,float diceFaceAlpha) {
        var newConnectedPlayer = new Player(id, name, avatarID, avatarBackgroundHex, avatarBackgroundAlpha, diceBodyHex, diceBodyAlpha, diceFaceHex, diceFaceAlpha);

        this.playerMap.put(id, newConnectedPlayer);
    }

    /**
     * Adds a new player to the map of this lobby.
     * @param datagram Entire {@code RegistrationRequestDatagram} object containing player's info.
     * @param clientID ID of the player.
     */
    public void addPlayer(RegistrationRequestDatagram datagram, String clientID) throws Exception {
        if (this.playerMap.containsKey(clientID)) {
            this.mainServer.getLogger().error("Duplicated player ID '{}'!", clientID);
            return;
        }

        var newConnectedPlayer = new Player(datagram, clientID);
        this.playerMap.put(clientID, newConnectedPlayer);
    }

    /**
     * Updates a player's information with their new data received from
     * themselves.
     * 
     * <p>To be sent to all other clients to update
     * @param updateDatagram {@code PlayerUpdateDatagram} object for the update.
     */
    public void updatePlayer(PlayerUpdateDatagram updateDatagram) {
        var targetPlayerID = updateDatagram.getId();
        var targetPlayer = this.getPlayer(targetPlayerID);

        targetPlayer.setName(updateDatagram.getName());
        targetPlayer.setAvatarID(updateDatagram.getAvatarID());
        targetPlayer.setAvatarBackgroundHex(updateDatagram.getAvatarBackgroundHex());
        targetPlayer.setAvatarBackgroundAlpha(updateDatagram.getAvatarBackgroundAlpha());
        targetPlayer.setDiceBodyHex(updateDatagram.getDiceBodyHex());
        targetPlayer.setDiceBodyAlpha(updateDatagram.getDiceBodyAlpha());
        targetPlayer.setDiceFaceHex(updateDatagram.getDiceFaceHex());
        targetPlayer.setDiceBodyAlpha(updateDatagram.getDiceFaceAlpha());

        // Performs broadcasting to all clients here!
    }

    /**
     * Removes a player from the map of this lobby.
     * @param targetID ID of the to be removed player.
     */
    public void removePlayer(String targetID) {
        this.playerMap.remove(targetID);
    }

    /**
     * Retrieves the target player based on input ID.
     * @param id Returned player ID.
     * @return A {@code Player} object.
     * @throws Exception 
     */
    public Player getPlayer(String id) {
        if (this.playerMap.containsKey(id)) return this.playerMap.get(id);
        else return null;
    }

    /**
     * Retrieves the entire mapping of all players of this game lobby.
     * @return A {@code HashMap<String, Player>} object.
     */
    public Map<String, Player> getPlayerMap() {
        return this.playerMap;
    }
}
