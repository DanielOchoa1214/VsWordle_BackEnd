package edu.eci.arsw.wordle.controllers;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.exceptions.LobbyException;
import edu.eci.arsw.wordle.services.LobbyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/lobbies")
public class LobbyAPIController {

    @Autowired
    LobbyServices lobbyServices = null;

    @GetMapping()
    public ResponseEntity<?> getLobbies() {
        try{
            ConcurrentMap<String, Lobby> lobbies = lobbyServices.getLobbies();
            return new ResponseEntity<>(lobbies, HttpStatus.FOUND);
        } catch (LobbyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> newLobby(@RequestBody Player player) {
        try{
            String idLobby = lobbyServices.newLobby(player);
            return new ResponseEntity<>(idLobby, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{idLobby}")
    public ResponseEntity<?> getLobby(@PathVariable("idLobby") String idLobby) {
        try{
            Lobby lobby = lobbyServices.getLobby(idLobby);
            return new ResponseEntity<>(lobby, HttpStatus.FOUND);
        } catch (LobbyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{idLobby}/startGame")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized ResponseEntity<?> startGame(@PathVariable("idLobby") String idLobby) {
        try {
            Lobby lobby = lobbyServices.getLobby(idLobby);
            Boolean success = lobbyServices.startGame(lobby);
            return new ResponseEntity<>(success, HttpStatus.ACCEPTED);
        } catch (LobbyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{idLobby}/winner")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized ResponseEntity<?> getLobbyWinner(@PathVariable("idLobby") String idLobby) {
        try{
            Lobby lobby = lobbyServices.getLobby(idLobby);
            List<Player> sortPlayers = lobbyServices.getLobbyWinner(lobby);
            return new ResponseEntity<>(sortPlayers, HttpStatus.FOUND);
        } catch (LobbyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{idLobby}/host")
    public ResponseEntity<?> getHost(@PathVariable("idLobby") String idLobby){
        try{
            Lobby lobby = lobbyServices.getLobby(idLobby);
            Player host = lobbyServices.getHost(lobby);
            return new ResponseEntity<>(host, HttpStatus.ACCEPTED);
        } catch (LobbyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
