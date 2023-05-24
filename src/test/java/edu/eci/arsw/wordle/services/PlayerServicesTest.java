package edu.eci.arsw.wordle.services;

import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.exceptions.LobbyException;
import edu.eci.arsw.wordle.persistence.exceptions.PlayerException;
import edu.eci.arsw.wordle.services.LobbyServices;
import edu.eci.arsw.wordle.services.PlayerServices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerServicesTest {
    @Mock
    private LobbiesInterface lobbiesInterfaceMock;
    @InjectMocks
    private PlayerServices playerServices;
    @Before
    public void beforeTests(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = LobbyException.class)
    public void whenPlayerExistTrowsException() throws LobbyException {
        // ARRANGE
        Player player1 = new Player("Julian");
        Player player2 = new Player("Julian");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player1);
        // ACT
        playerServices.addPlayer(player2, lobby);
        // ASSERT
    }

    @Test(expected = LobbyException.class)
    public void whenLobbyIsClosedTrowsException() throws LobbyException {
        // ARRANGE
        Player player = new Player("Julian");
        Lobby lobby = new Lobby();
        lobby.startGame();
        // ACT
        playerServices.addPlayer(player, lobby);
        // ASSERT
    }

    @Test(expected = LobbyException.class)
    public void whenPlayerNicknameIsEmptyTrowsException() throws LobbyException {
        // ARRANGE
        Player player = new Player("");
        Lobby lobby = new Lobby();
        // ACT
        playerServices.addPlayer(player, lobby);
        // ASSERT
    }

    @Test
    public void whenNewPlayerIsValidAddToTheLobby() throws LobbyException {
        // ARRANGE
        Player player = new Player("Julian");
        Lobby lobby = new Lobby();
        Mockito.when(lobbiesInterfaceMock.addLobby(player)).thenReturn(String.valueOf(true));
        // ACT
        boolean success = playerServices.addPlayer(player, lobby);
        // ASSERT
        assertTrue(success);
    }

    @Test(expected = PlayerException.class)
    public void whenNicknameNoExistTrowsException() throws PlayerException, LobbyException {
        // ARRANGE
        Player player = new Player("Yo");
        Lobby lobby = new Lobby();
        // ACT
        playerServices.removePlayer(player, lobby);
        // ASSERT
    }

    @Test(expected = LobbyException.class)
    public void whenNicknameIsEmptyTrowsException() throws PlayerException, LobbyException {
        // ARRANGE
        Player player = new Player("");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        // ACT
        playerServices.removePlayer(player, lobby);
        // ASSERT
    }

    @Test
    public void whenPlayerExistRemoveItFromLobby() throws PlayerException, LobbyException {
        // ARRANGE
        Player player = new Player("Julian");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        // ACT
        playerServices.removePlayer(player, lobby);
        // ASSERT
        assertEquals(0, lobby.getPlayers().size());
    }

    @Test(expected = PlayerException.class)
    public void whenPlayerIsNotFoundTrowsException() throws PlayerException {
        // ARRANGE
        String nickname = "yo";
        Lobby lobby = new Lobby();
        // ACT
        playerServices.getPlayer(nickname, lobby);
        // ASSERT
    }

    @Test
    public void whenPlayerIsFoundReturnPlayer() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        String nickname = "yo";
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        // ACT
        Player player_return = playerServices.getPlayer(nickname, lobby);
        // ASSERT
        assertEquals(player, player_return);
    }

    @Test(expected = PlayerException.class)
    public void whenPlayerListIsEmptyTrowsException() throws PlayerException {
        // ARRANGE
        Lobby lobby = new Lobby();
        // ACT
        playerServices.getPlayerList(lobby);
        // ASSERT
    }

    @Test
    public void whenPlayerListIsNotEmptyReturnIt() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        // ACT
        List<Player> list_player = playerServices.getPlayerList(lobby);
        // ASSERT
        assertEquals(1, list_player.size());
    }

    @Test
    public void whenThereAreMissingPlayersReturnIt() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        Player player1 = new Player("yo1");
        Player player2 = new Player("yo2");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        lobby.addPlayer(player1);
        lobby.addPlayer(player2);
        // ACT
        List<String> list_player = playerServices.getMissingPlayers(player.getNickname(), lobby);
        // ASSERT
        assertEquals(2, list_player.size());
    }

    @Test(expected = PlayerException.class)
    public void whenPlayerNotFoundTrowsExceptionOnCorrectLetter() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        Lobby lobby = new Lobby();
        // ACT
        playerServices.addCorrectLetter(player, lobby);
        // ASSERT
    }

    @Test
    public void whenPlayerFoundAddCorrectLetter() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        // ACT
        playerServices.addCorrectLetter(player, lobby);
        // ASSERT
        assertEquals(1, player.getCorrectLetters());
    }

    @Test(expected = PlayerException.class)
    public void whenPlayerNotFoundTrowsExceptionOnWrongLetter() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        Lobby lobby = new Lobby();
        // ACT
        playerServices.addCorrectLetter(player, lobby);
        // ASSERT
    }

    @Test
    public void whenPlayerFoundAddWrongLetter() throws PlayerException {
        // ARRANGE
        Player player = new Player("yo");
        Lobby lobby = new Lobby();
        lobby.addPlayer(player);
        // ACT
        playerServices.addWrongLetter(player, lobby);
        // ASSERT
        assertEquals(1, player.getWrongLetters());
    }
}
