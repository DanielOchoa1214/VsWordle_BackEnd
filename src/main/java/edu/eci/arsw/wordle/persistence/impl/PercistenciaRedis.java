package edu.eci.arsw.wordle.persistence.impl;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PercistenciaRedis implements LobbiesInterface{

    @Autowired
    private RedisTemplate<String, Lobby> redisTemplate;

    @Override
    public Lobby getLobby(String idLobby) {
        return redisTemplate.opsForValue().get(idLobby);
    }

    @Override
    public ConcurrentHashMap<String, Lobby> getLobbies() {
        Set<String> keys = redisTemplate.keys("*");
        ConcurrentHashMap<String, Lobby> lobbies = new ConcurrentHashMap<>();
        for(String id : keys){
            lobbies.put(id, getLobby(id));
        }
        return lobbies;
    }

    @Override
    public String addLobby(Player player) {
        ValueOperations<String, Lobby> valueOp = redisTemplate.opsForValue();
        Lobby lobby = new Lobby();
        lobby.setHost(player);
        lobby.addPlayer(player);
        valueOp.set(lobby.getId(), lobby);
        return lobby.getId();
    }

    @Override
    public void resetLobby(String idLobby) {
        Lobby blanckLobby = new Lobby();
        blanckLobby.setId(idLobby);
        redisTemplate.opsForValue().set(blanckLobby.getId(), blanckLobby);
    }

    @Override
    public void deleteLobby(String idLobby) {
        redisTemplate.delete(idLobby);
    }

    @Override
    public void updateLobby(Lobby newLobby) {
        redisTemplate.opsForValue().set(newLobby.getId(), newLobby);
    }
}
