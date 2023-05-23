package edu.eci.arsw.wordle;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Palabra;
import edu.eci.arsw.wordle.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LobbyTest {

    private Lobby lobby;

    @BeforeEach
    public void setup() {
        lobby = new Lobby();
    }

    @Test
    public void testAddPlayer() {
        Player player = new Player("John");
        boolean added = lobby.addPlayer(player);

        Assertions.assertTrue(added);
        Assertions.assertEquals(player, lobby.getHost());
        Assertions.assertTrue(lobby.getPlayers().contains(player));
    }



    @Test
    public void testGetPalabra() {
        List<Palabra> palabraList = lobby.getPalabras();

        for (int i = 0; i < 10; i++) {
            Palabra palabra = lobby.getPalabra(i);
            Assertions.assertTrue(palabraList.contains(palabra));
        }
    }

    @Test
    public void testStartGame() {
        boolean isClosed = lobby.startGame();
        Assertions.assertTrue(isClosed);
    }



    @Test
    public void testResetLobby() {
        Player player = new Player("John");
        lobby.addPlayer(player);
        lobby.startGame();

        lobby.resetLobby();

        Assertions.assertNull(lobby.getHost());
        Assertions.assertFalse(lobby.getIsClosed().get());
        Assertions.assertFalse(lobby.getIsFinished().get());
        Assertions.assertEquals(10, lobby.getPalabras().size());
        Assertions.assertTrue(lobby.getPlayers().isEmpty());
    }
}
