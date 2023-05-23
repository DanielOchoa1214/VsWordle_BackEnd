package edu.eci.arsw.wordle.services;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Palabra;
import edu.eci.arsw.wordle.persistence.*;
import edu.eci.arsw.wordle.persistence.exceptions.PalabrasException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalabraServices {

    @Autowired
    private LobbiesInterface lobbies;

    public boolean proveWord(String palabra, int round, String nickname, Lobby lobby) {
        Palabra wordInt = new Palabra(palabra);
        Palabra word = lobby.getPalabra(round);
        synchronized (word) {
            if(!word.isTaken() && wordInt.equals(lobby.getPalabra(round))) {
                word.setTaken(true);
                lobby.getPlayer(nickname).addRoundWon();
                lobbies.updateLobby(lobby);
                return true;
            }
        }
        return false;
    }

    public String getWord(int round, Lobby lobby) throws PalabrasException {
        try {
            return lobby.getPalabra(round).getText();
        } catch (IndexOutOfBoundsException e) {
            throw new PalabrasException(PalabrasException.NOT_FOUND_PALABRA);
        }
    }

    public List<Palabra> getWords(Lobby lobby) {
        return lobby.getPalabras();
    }

}
