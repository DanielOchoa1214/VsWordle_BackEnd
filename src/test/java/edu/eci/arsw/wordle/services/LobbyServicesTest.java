package edu.eci.arsw.wordle.services;


import edu.eci.arsw.wordle.model.Lobby;
import edu.eci.arsw.wordle.model.Player;
import edu.eci.arsw.wordle.persistence.LobbiesInterface;
import edu.eci.arsw.wordle.persistence.exceptions.LobbyException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LobbyServicesTest {

    @Mock
    private LobbiesInterface lobbiesInterfaceMock;

    @InjectMocks
    private LobbyServices lobbyServices;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private Lobby lobby;

    @Test
    public void testNewLobby_validPlayer_newLobbyIdReturned() throws LobbyException {
        // Arrange
        Player player = new Player("John");
        String expectedLobbyId = "lobby1";
        when(lobbiesInterfaceMock.addLobby(player)).thenReturn(expectedLobbyId);

        // Act
        String result = lobbyServices.newLobby(player);

        // Assert
        assertEquals(expectedLobbyId, result);
        verify(lobbiesInterfaceMock, times(1)).addLobby(player);
    }


}






