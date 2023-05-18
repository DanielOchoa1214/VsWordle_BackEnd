package edu.eci.arsw.wordle.persistence.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PercistenciaRedis implements LobbiesInterface{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Lobby getLobby(String idLobby) {
        return fromString(redisTemplate.opsForValue().get(idLobby));
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
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        Lobby lobby = new Lobby();
        lobby.setHost(player);
        lobby.addPlayer(player);
        valueOp.set(lobby.getId(), toString(lobby));
        return lobby.getId();
    }

    @Override
    public void resetLobby(String idLobby) {
        Lobby blanckLobby = new Lobby();
        blanckLobby.setId(idLobby);
        redisTemplate.opsForValue().set(blanckLobby.getId(), toString(blanckLobby));
    }

    @Override
    public void deleteLobby(String idLobby) {
        redisTemplate.delete(idLobby);
    }

    @Override
    public void updateLobby(Lobby newLobby) {
        redisTemplate.opsForValue().set(newLobby.getId(), toString(newLobby));
    }

    private static Lobby fromString( String s ){
        try {
            byte [] data = Base64.getDecoder().decode( s );
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
            Lobby o  = (Lobby) ois.readObject();
            ois.close();
            return o;
        } catch (IOException | ClassNotFoundException e){
            return null;
        }

    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( o );
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e){
            return null;
        }
    }
}
