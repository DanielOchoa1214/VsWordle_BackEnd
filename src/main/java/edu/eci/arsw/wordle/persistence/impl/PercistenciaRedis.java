package edu.eci.arsw.wordle.persistence.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PercistenciaRedis implements LobbiesInterface{

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Lobby getLobby(String idLobby) {
        return null;
    }

    @Override
    public ConcurrentHashMap<String, Lobby> getLobbies() {
        return null;
    }

    @Override
    public String addLobby(Player player) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        Lobby lobby = new Lobby();
        lobby.setHost(player);
        lobby.addPlayer(player);
        try {
            valueOp.set(lobby.getId(), objectMapper.writeValueAsString(lobby));
        } catch (JsonProcessingException ignored){}
        return lobby.getId();
    }

    @Override
    public void resetLobby(String idLobby) {

    }

    @Override
    public void deleteLobby(String idLobby) {

    }

    @Override
    public void updateLobby(Lobby newLobby) {

    }
}
