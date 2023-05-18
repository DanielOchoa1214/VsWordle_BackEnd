package edu.eci.arsw.wordle.controllers;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.services.LobbyServices;
import edu.eci.arsw.wordle.services.PlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
public class WebSocketHandler extends StompSessionHandlerAdapter {

    @Autowired
    SimpMessagingTemplate msgt;
    @Autowired
    private LobbyServices lobbyServices;
    @Autowired
    private PlayerServices playerServices;

    @MessageMapping("/startGame.{idLobby}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void handleStartEvent(@DestinationVariable String idLobby) throws Exception {
        lobbyServices.startGame(lobbyServices.getLobby(idLobby));
    }

    @MessageMapping("/endGame.{idLobby}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void handleEndEvent(@DestinationVariable String idLobby) throws Exception {
        List<Player> sortPlayers = lobbyServices.getLobbyWinner(lobbyServices.getLobby(idLobby));
        msgt.convertAndSend("/topic/endGame." + idLobby, sortPlayers);
    }

    @MessageMapping("/removePlayer.{idLobby}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void handleRemovePlayerEvent(Player player, @DestinationVariable String idLobby) throws Exception {
        playerServices.removePlayer(player, lobbyServices.getLobby(idLobby));
        msgt.convertAndSend("/topic/removePlayer." + idLobby, player);
    }

    @MessageMapping("/wrongLetter.{idLobby}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void handleWrongLetterEvent(Player player, @DestinationVariable String idLobby) throws Exception {
        Lobby lobby = lobbyServices.getLobby(idLobby);
        playerServices.addWrongLetter(player, lobby);
    }

    @MessageMapping("/correctLetter.{idLobby}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void handleCorrectLetterEvent(Player player, @DestinationVariable String idLobby) throws Exception {
        Lobby lobby = lobbyServices.getLobby(idLobby);
        playerServices.addCorrectLetter(player, lobby);
    }
}