package edu.eci.arsw.wordle.controllers;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.persistence.exceptions.LobbyException;
import edu.eci.arsw.wordle.persistence.exceptions.PalabrasException;
import edu.eci.arsw.wordle.services.LobbyServices;
import edu.eci.arsw.wordle.services.PalabraServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "arsw-vswordle.eastus.cloudapp.azure.com")
@RequestMapping(value = "/lobbies/{idLobby}/palabras")
public class PalabrasAPIController {

    @Autowired
    PalabraServices palabraServices = null;
    @Autowired
    LobbyServices lobbyServices = null;

    @GetMapping()
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized ResponseEntity<?> proveWord(@RequestParam("palabra") String palabra, @RequestParam("round")  int round, @RequestParam("nickname") String nickname, @PathVariable("idLobby") String idLobby) {
        try {
            Lobby lobby = lobbyServices.getLobby(idLobby);
            boolean data = palabraServices.proveWord(palabra, round, nickname, lobby);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (LobbyException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{round}")
    public ResponseEntity<?> getWord(@PathVariable("round")  int round, @PathVariable("idLobby") String idLobby) {
        try{
            Lobby lobby = lobbyServices.getLobby(idLobby);
            String data = palabraServices.getWord(round, lobby);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (PalabrasException | LobbyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /*@GetMapping()
    public ResponseEntity<?> getWords(@PathVariable("idLobby") String idLobby) {
        try{
            System.out.println("get Words");
            Lobby lobby = lobbyServices.getLobby(idLobby);
            List<Palabra> data = palabraServices.getWords(lobby);
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>("Algo ocurrio, lo sentimos :(", HttpStatus.NOT_FOUND);
        }
    }*/
}
