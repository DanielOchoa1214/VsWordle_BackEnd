package edu.eci.arsw.wordle.services;


import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.exceptions.LobbyException;
import edu.eci.arsw.wordle.services.LobbyServices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.*;

public class LobbyServicesTest {

    @Mock
    private LobbiesInterface lobbiesInterfaceMock;
    @InjectMocks
    private LobbyServices lobbyServices;

    @Before
    public void beforeTests(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = LobbyException.class)
    public void whenPlayerIsNullNewLobbyTrowsException() throws LobbyException {
        // ARRANGE
        // ACT
        lobbyServices.newLobby(null);
        // ASSERT
    }

    @Test(expected = LobbyException.class)
    public void whenPlayerNicknameEmptyNewLobbyTrowsException() throws LobbyException {
        // ARRANGE
        Player emptyPlayer = new Player("");
        // ACT
        lobbyServices.newLobby(emptyPlayer);
        // ASSERT
    }

    @Test
    public void whenPlayerIsValidNewLobbyIsCreated() throws LobbyException {
        // ARRANGE
        Player player = new Player("Tu madre");
        String newLobbyId = "test";
        Mockito.when(lobbiesInterfaceMock.addLobby(player)).thenReturn(newLobbyId);
        // ACT
        String lobbyId = lobbyServices.newLobby(player);
        // ASSERT
        assertEquals(newLobbyId, lobbyId);
    }

    @Test(expected = LobbyException.class)
    public void whenIdDoesNotExistsGetLobbyThrowsException() throws LobbyException {
        // ARRANGE
        String invalidId = "NOT HERE";
        Mockito.when(lobbiesInterfaceMock.getLobby(invalidId)).thenReturn(null);
        // ACT
        lobbyServices.getLobby(invalidId);
        // ASSERT
    }

    @Test
    public void whenIdIsValidGetLobbyReturnsLobby() throws LobbyException {
        // ARRANGE
        String validId = "HERE";
        Lobby lobby = new Lobby();
        lobby.setId(validId);
        Mockito.when(lobbiesInterfaceMock.getLobby(validId)).thenReturn(lobby);
        // ACT
        Lobby lobby1 = lobbyServices.getLobby(validId);
        // ASSERT
        assertEquals(lobby, lobby1);
    }

    @Test(expected = LobbyException.class)
    public void whenLobbiesDoNotExistGetLobbiesThrowsException() throws LobbyException {
        // ARRANGE
        Mockito.when(lobbiesInterfaceMock.getLobbies()).thenReturn(null);
        // ACT
        lobbyServices.getLobbies();
        // ASSERT
    }

    @Test
    public void whenLobbiesExistGetLobbiesReturnsHash() throws LobbyException {
        // ARRANGE
        ConcurrentHashMap<String, Lobby> lobbies = new ConcurrentHashMap<>();
        lobbies.put("test", new Lobby("test"));
        lobbies.put("abcd", new Lobby("abcd"));
        Mockito.when(lobbiesInterfaceMock.getLobbies()).thenReturn(lobbies);
        // ACT
        ConcurrentMap<String, Lobby> lobbies1 = lobbyServices.getLobbies();
        // ASSERT
        assertEquals(lobbies, lobbies1);
    }

    @Test(expected = LobbyException.class)
    public void whenLobbyIsClosedStartGameThrowsException() throws LobbyException {
        // ARRANGE
        Lobby lobby = new Lobby();
        lobby.startGame();
        // ACT
        lobbyServices.startGame(lobby);
        // ASSERT
    }

    @Test
    public void whenLobbyIsNotClosedStartGameThrowsException() throws LobbyException {
        // ARRANGE
        Lobby lobby = new Lobby();
        // ACT
        boolean closed = lobbyServices.startGame(lobby);
        // ASSERT
        assertTrue(closed);
    }

    @Test
    public void whenLobbyIsNotFinishedThenGetLobbyWinnerReturnsStatistics() throws LobbyException {
        // ARRANGE
        Lobby lobby = new Lobby("TEST");
        Player yo = new Player("Yo");
        Player el = new Player("El");
        lobby.addPlayer(yo);
        lobby.addPlayer(el);

        yo.addRoundWon();

        List<Player> statistics = new ArrayList<>();
        statistics.add(yo);
        statistics.add(el);
        // ACT
        List<Player> sortPlayers = lobbyServices.getLobbyWinner(lobby);
        // ASSERT
        assertEquals(statistics, sortPlayers);
    }

    @Test
    public void whenGettingHostThenReturnsHost(){
        // ARRANGE
        Player host = new Player("Putito");
        Lobby lobby = new Lobby();
        lobby.setHost(host);
        // ACT
        Player serviceHost = lobbyServices.getHost(lobby);
        // ASSERT
        assertEquals(host, serviceHost);
    }
}






