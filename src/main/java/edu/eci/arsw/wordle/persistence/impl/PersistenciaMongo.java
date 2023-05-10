package edu.eci.arsw.wordle.persistence.impl;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.mongointerface.MongoDataBaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// @Service
public class PersistenciaMongo implements LobbiesInterface{

    @Autowired
    private MongoDataBaseInterface mongoDB;

    private boolean isIdRepeated(Lobby lobby) {
        List<Lobby> lobbies = mongoDB.findAll();
        for (Lobby lobbyItem : lobbies){
            if(lobby.getId().equals(lobbyItem.getId())){
                return true;
            }
        }
        return false;
    }
    @Override
    public Lobby getLobby(String idLobby) {
        return mongoDB.getLobby(idLobby);
    }

    @Override
    public ConcurrentHashMap<String, Lobby> getLobbies() {
        List<Lobby> lobbies = mongoDB.findAll();
        ConcurrentHashMap<String, Lobby> lobbiesMap = new ConcurrentHashMap<>();
        for(Lobby lobby : lobbies){
            lobbiesMap.put(lobby.getId(), lobby);
        }
        return lobbiesMap;
    }

    @Override
    public String addLobby(Player player) {
        Lobby lobby = new Lobby();
        lobby.setHost(player);
        lobby.addPlayer(player);
        mongoDB.insert(lobby);
        return lobby.getId();
    }

    @Override
    public void resetLobby(String idLobby) {
        // Habria que remover el reset Lobby para mantener todo consistente
        Lobby blankLobby = new Lobby();
        blankLobby.setId(idLobby);
        mongoDB.save(blankLobby);
    }

    @Override
    public void deleteLobby(String idLobby) {
        mongoDB.delete(idLobby);
    }

    @Override
    public void updateLobby(Lobby newLobby) {
        mongoDB.save(newLobby);
    }
}
