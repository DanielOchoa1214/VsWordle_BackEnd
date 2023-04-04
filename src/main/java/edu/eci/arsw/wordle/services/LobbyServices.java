package edu.eci.arsw.wordle.services;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.LobbyException;
import edu.eci.arsw.wordle.persistence.PlayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LobbyServices {

    @Autowired
    private LobbiesInterface lobbies;

    public String newLobby (Player player) throws  LobbyException{
        if(player == null) throw new LobbyException(LobbyException.NOT_HOST);
        return lobbies.addLobby(player);
    }

    public Lobby getLobby(String idLobby) throws LobbyException {
        if(lobbies.getLobby(idLobby) == null) throw new LobbyException(LobbyException.LOBBY_NOT_FOUND);
        return lobbies.getLobby(idLobby);
    }

    public ConcurrentMap<String, Lobby> getLobbies() throws LobbyException {
        if(lobbies.getLobbies() == null) throw new LobbyException(LobbyException.LOBBY_NOT_FOUND);
        return lobbies.getLobbies();
    }
    public boolean startGame(Lobby lobby) throws LobbyException {
        if (!lobby.startGame()) throw new LobbyException(LobbyException.IS_CLOSED);
        return lobby.startGame();
    }

    public List<Player> getLobbyWinner(Lobby lobby) throws LobbyException {
        if(lobby.getIsFinished().get()) throw new LobbyException(LobbyException.IS_NOT_FINISHED);
        List<Player> sortPlayers = lobby.statistics();
        lobbies.resetLobby(lobby.getIdLobby());
        deleteLobby(lobby);
        return sortPlayers;
    }

    public Player getHost(Lobby lobby) {
        return lobby.getHost();
    }

    private void deleteLobby(Lobby lobby) {
        int players = lobbies.getLobby(lobby.getIdLobby()).getPlayers().size();
        setTimeout(() -> {
            int playersAfter = lobbies.getLobby(lobby.getIdLobby()).getPlayers().size();
            if(players == playersAfter) {
                lobbies.deleteLobby(lobby.getIdLobby());
            }
        }, 300000);
    }

    private void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
